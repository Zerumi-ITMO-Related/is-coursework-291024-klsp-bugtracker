package io.github.zerumi.is_coursework_291024_klsp_bugtracker.interceptor

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.annotation.RequirePermission
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Permissions
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.checkPermission
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.service.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

@Component
class RequirePermissionInterceptor(val userService: UserService) : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler is HandlerMethod) {
            val method = handler.getMethodAnnotation(RequirePermission::class.java)
                ?: handler.method.declaringClass.getAnnotation(RequirePermission::class.java)

            if (method != null) {
                val currentUser = userService.getCurrentUser()

                if (!checkPermission(currentUser.permissionSet.permissions, method.permission) && !checkPermission(
                        currentUser.permissionSet.permissions, Permissions.PRIVILEGED
                    )
                ) {
                    response.status = HttpServletResponse.SC_FORBIDDEN
                    return false
                }
            }
        }
        return true
    }
}
