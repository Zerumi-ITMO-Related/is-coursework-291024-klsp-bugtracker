package io.github.zerumi.is_coursework_291024_klsp_bugtracker.controller

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.annotation.RequirePermission
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.SprintDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.SprintInfo
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.SprintRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.toDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Permissions
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Sprint
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.service.SprintService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.parameters.P
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/sprint")
class SprintController(
    val sprintService: SprintService
) {
    @GetMapping("/{id}")
    fun getSprint(@PathVariable id: Long): SprintDTO {
        return toDTO(sprintService.getById(id))
    }

    @PostMapping
    @RequirePermission(Permissions.MANAGE_SPRINT)
    @SecurityRequirement(name = "Bearer Authentication")
    fun createSprint(@RequestBody sprintRequestDTO: SprintRequestDTO) {
        return sprintService.createSprint(sprintRequestDTO)
    }

    @PutMapping
    @RequirePermission(Permissions.MANAGE_SPRINT)
    @SecurityRequirement(name = "Bearer Authentication")
    fun updateSprint(@RequestBody @P("sprint") sprintInfo: SprintInfo) {
        return sprintService.updateSprint(sprintInfo)
    }

    @DeleteMapping
    @RequirePermission(Permissions.MANAGE_SPRINT)
    @SecurityRequirement(name = "Bearer Authentication")
    fun deleteSprint(@RequestBody @P("sprint") sprintInfo: SprintInfo) {
        return sprintService.deleteSprint(sprintInfo)
    }
}
