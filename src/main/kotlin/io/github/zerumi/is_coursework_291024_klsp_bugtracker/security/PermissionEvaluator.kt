package io.github.zerumi.is_coursework_291024_klsp_bugtracker.security

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Permissions
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.User
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.checkPermission
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.io.Serializable

@Component
class PermissionEvaluatorImpl : PermissionEvaluator {
    override fun hasPermission(authentication: Authentication?, targetDomainObject: Any?, permission: Any?): Boolean {
        throw NotImplementedError()
    }

    override fun hasPermission(
        authentication: Authentication?, targetId: Serializable?, targetType: String?, permission: Any?
    ): Boolean {
        if (authentication?.principal !is User || targetId !is OwnedObject || permission !is String) {
            return false
        }

        val principal = authentication.principal as User

        val owner = targetId.ownership()
        if (owner.id == principal.id) return true

        val permissions = principal.permissionSet.permissions
        val requiredPermission = Permissions.valueOf(permission)

        return checkPermission(permissions, requiredPermission) || checkPermission(permissions, Permissions.PRIVILEGED)
    }
}
