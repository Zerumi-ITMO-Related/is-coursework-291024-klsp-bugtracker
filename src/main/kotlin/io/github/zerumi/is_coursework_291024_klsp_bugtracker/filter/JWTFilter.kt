package io.github.zerumi.is_coursework_291024_klsp_bugtracker.filter

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.service.JWTService
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.service.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JWTFilter(
    val jwtService: JWTService,
    val userService: UserService,
) : OncePerRequestFilter() {
    companion object {
        const val BEARER_PREFIX = "Bearer "
        const val AUTHORIZATION = "Authorization"
    }

    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain
    ) {
        val authHeader = request.getHeader(AUTHORIZATION) ?: ""
        if (authHeader.isEmpty() || !authHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response)
            return
        }

        val jwt = authHeader.substring(BEARER_PREFIX.length)
        val username = jwtService.extractUserName(jwt)

        if (username.isNotEmpty() && SecurityContextHolder.getContext().authentication == null) {
            val userDetails: UserDetails = userService.loadUserByUsername(username)

            if (jwtService.isTokenValid(jwt, userDetails)) {
                val context = SecurityContextHolder.createEmptyContext()

                val authToken = UsernamePasswordAuthenticationToken.authenticated(
                    userDetails, null, userDetails.authorities
                )

                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                context.authentication = authToken
                SecurityContextHolder.setContext(context)
            }
        }
        filterChain.doFilter(request, response)
    }
}
