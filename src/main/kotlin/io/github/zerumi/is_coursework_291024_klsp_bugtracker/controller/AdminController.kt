package io.github.zerumi.is_coursework_291024_klsp_bugtracker.controller

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.annotation.RequirePermission
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.*
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Permissions
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.service.AdminService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/admin")
class AdminController(
    val adminService: AdminService,
) {
    @GetMapping("{id}")
    fun getPermissionSet(@PathVariable id: Long): PermissionSetDTO =
        toDTO(adminService.getById(id))

    @PostMapping
    @RequirePermission(Permissions.MANAGE_USER_PERMISSIONS)
    @SecurityRequirement(name = "Bearer Authentication")
    fun createPermissionSet(@RequestBody permissionSetRequestDTO: PermissionSetRequestDTO): PermissionSetDTO =
        toDTO(adminService.createPermissionSet(permissionSetRequestDTO))

    @PutMapping
    @RequirePermission(Permissions.MANAGE_USER_PERMISSIONS)
    @SecurityRequirement(name = "Bearer Authentication")
    fun updatePermissionSet(@RequestBody permissionSetInfo: PermissionSetInfo): PermissionSetDTO =
        toDTO(adminService.updatePermissionSet(permissionSetInfo))

    @DeleteMapping
    @RequirePermission(Permissions.MANAGE_USER_PERMISSIONS)
    @SecurityRequirement(name = "Bearer Authentication")
    fun deletePermissionSet(@RequestBody permissionSetInfo: PermissionSetInfo): Unit =
        adminService.deletePermissionSet(permissionSetInfo)

    @PutMapping("{userId}/toPermissionSet/{permissionSetId}")
    @RequirePermission(Permissions.MANAGE_USER_PERMISSIONS)
    @SecurityRequirement(name = "Bearer Authentication")
    fun linkUserToPermissionSet(@PathVariable userId: Long, @PathVariable permissionSetId: Long): UserDTO =
        toDTO(adminService.linkUserToPermissionSet(userId, permissionSetId))
}
