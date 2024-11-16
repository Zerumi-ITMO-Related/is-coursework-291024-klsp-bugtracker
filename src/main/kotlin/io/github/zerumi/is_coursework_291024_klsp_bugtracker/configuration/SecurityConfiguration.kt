package io.github.zerumi.is_coursework_291024_klsp_bugtracker.configuration

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.filter.JWTFilter
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.security.PermissionEvaluatorImpl
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfiguration(
    val jwtFilter: JWTFilter,
    val userService: UserService,
    val mvc: MvcRequestMatcher.Builder,
    val permissionEvaluatorImpl: PermissionEvaluatorImpl
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.cors { it.disable() }.csrf { it.disable() }
        http.csrf { csrf ->
            csrf.ignoringRequestMatchers("/**")
        }
        http.headers { headersConfigurer -> headersConfigurer.frameOptions { it.sameOrigin() } }

        val corsConfiguration = CorsConfiguration()

        corsConfiguration.allowCredentials = true
        corsConfiguration.addAllowedOrigin("*")
        corsConfiguration.addAllowedHeader("*")
        corsConfiguration.addAllowedMethod("*")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfiguration)

        val corsFilter = CorsFilter(source)

        http.authorizeHttpRequests { ar ->
            ar
                .requestMatchers(mvc.pattern("/api/v1/issue/page/**")).permitAll()
                .requestMatchers(mvc.pattern("/api/v1/event/page/**")).permitAll()
                .requestMatchers(mvc.pattern("/api/v1/epic/page/**")).permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/tag/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/sprint/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/issue/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/event/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/epic/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/comment/**").permitAll()
                .requestMatchers(mvc.pattern("/api/v1/auth/**")).permitAll()
                .requestMatchers(
                    mvc.pattern("/swagger-ui/**"), mvc.pattern("/swagger-resources/*"), mvc.pattern("/v3/api-docs/**")
                ).permitAll()
                .anyRequest().authenticated()
        }.httpBasic(Customizer.withDefaults())

        http.authenticationProvider(authenticationProvider())
        http.addFilter(corsFilter)
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userService)
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager = config.authenticationManager

    @Bean
    fun createExpressionHandler(): MethodSecurityExpressionHandler {
        val expressionHandler = DefaultMethodSecurityExpressionHandler()
        expressionHandler.setPermissionEvaluator(permissionEvaluatorImpl)
        return expressionHandler
    }
}
