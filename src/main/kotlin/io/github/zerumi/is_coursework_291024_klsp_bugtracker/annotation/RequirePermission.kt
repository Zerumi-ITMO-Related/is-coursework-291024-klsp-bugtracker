package io.github.zerumi.is_coursework_291024_klsp_bugtracker.annotation

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Permissions

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class RequirePermission(val permission: Permissions)

/* ???
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class RequirePermissions(val permissions: Array<Permissions>)
*/