package io.github.zerumi.is_coursework_291024_klsp_bugtracker.controller

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.annotation.RequirePermission
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.SprintDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.SprintInfo
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.SprintRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.toDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Permissions
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.service.SprintService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/sprint")
class SprintController(
    val sprintService: SprintService
) {
    @GetMapping("/{id}")
    fun getSprint(@PathVariable id: Long): SprintDTO =
        toDTO(sprintService.getById(id))

    @PostMapping
    @RequirePermission(Permissions.MANAGE_SPRINT)
    @SecurityRequirement(name = "Bearer Authentication")
    fun createSprint(@RequestBody sprintRequestDTO: SprintRequestDTO): SprintDTO =
        toDTO(sprintService.createSprint(sprintRequestDTO))

    @PutMapping
    @RequirePermission(Permissions.MANAGE_SPRINT)
    @SecurityRequirement(name = "Bearer Authentication")
    fun updateSprint(@RequestBody sprintInfo: SprintInfo): SprintDTO =
        toDTO(sprintService.updateSprint(sprintInfo))

    @DeleteMapping
    @RequirePermission(Permissions.MANAGE_SPRINT)
    @SecurityRequirement(name = "Bearer Authentication")
    fun deleteSprint(@RequestBody sprintInfo: SprintInfo): Unit =
        sprintService.deleteSprint(sprintInfo)
}
