package io.github.zerumi.is_coursework_291024_klsp_bugtracker.configuration

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.interceptor.RequirePermissionInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMVCConfiguration(
    val requirePermissionInterceptor: RequirePermissionInterceptor
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        // Custom interceptor, add intercept path and exclude intercept path
        registry.addInterceptor(requirePermissionInterceptor).addPathPatterns("/**")
    }
}
