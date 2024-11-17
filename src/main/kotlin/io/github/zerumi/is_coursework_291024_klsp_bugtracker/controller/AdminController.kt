package io.github.zerumi.is_coursework_291024_klsp_bugtracker.controller

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.annotation.RequirePermission
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.*
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Permissions
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.service.AdminService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/admin")
@Tag(name = "Admin API", description = "Endpoints for managing users & it's permissions")
class AdminController(
    val adminService: AdminService,
) {
    @GetMapping("permissionSet/{id}")
    @Operation(summary = "Get a permission set by its id")
    fun getPermissionSet(@PathVariable id: Long): PermissionSetDTO =
        toDTO(adminService.getById(id))

    @PostMapping("permissionSet")
    @RequirePermission(Permissions.MANAGE_USER_PERMISSIONS)
    @Operation(
        summary = "Create a permission set",
        description = "This operation requires MANAGE_USER_PERMISSIONS permission"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    fun createPermissionSet(@RequestBody permissionSetRequestDTO: PermissionSetRequestDTO): PermissionSetDTO =
        toDTO(adminService.createPermissionSet(permissionSetRequestDTO))

    @PutMapping("permissionSet")
    @RequirePermission(Permissions.MANAGE_USER_PERMISSIONS)
    @Operation(
        summary = "Update a permission set",
        description = "This operation requires MANAGE_USER_PERMISSIONS permission"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    fun updatePermissionSet(@RequestBody permissionSetInfo: PermissionSetInfo): PermissionSetDTO =
        toDTO(adminService.updatePermissionSet(permissionSetInfo))

    @DeleteMapping("permissionSet")
    @RequirePermission(Permissions.MANAGE_USER_PERMISSIONS)
    @Operation(
        summary = "Delete a permission set",
        description = "This operation requires MANAGE_USER_PERMISSIONS permission"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    fun deletePermissionSet(@RequestBody permissionSetInfo: PermissionSetInfo): Unit =
        adminService.deletePermissionSet(permissionSetInfo)

    @PutMapping("permissionSet/{userId}/toPermissionSet/{permissionSetId}")
    @RequirePermission(Permissions.MANAGE_USER_PERMISSIONS)
    @Operation(
        summary = "Link user to permission set",
        description = "This operation requires MANAGE_USER_PERMISSIONS permission"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    fun linkUserToPermissionSet(@PathVariable userId: Long, @PathVariable permissionSetId: Long): UserDTO =
        toDTO(adminService.linkUserToPermissionSet(userId, permissionSetId))
}
