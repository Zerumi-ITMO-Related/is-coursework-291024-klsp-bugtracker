package io.github.zerumi.is_coursework_291024_klsp_bugtracker.controller

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.annotation.RequirePermission
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.SprintDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.SprintInfo
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.SprintRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.toDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Permissions
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.service.SprintService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/sprint")
@Tag(name = "Scrum Sprint API", description = "Endpoints for managing sprints")
class SprintController(
    val sprintService: SprintService
) {
    @GetMapping("/{id}")
    @Operation(summary = "Get a sprint by its id")
    fun getSprint(@PathVariable id: Long): SprintDTO =
        toDTO(sprintService.getById(id))

    @PostMapping
    @RequirePermission(Permissions.MANAGE_SPRINT)
    @Operation(
        summary = "Create scrum sprint",
        description = "This operation requires MANAGE_SPRINT permission",
    )
    @SecurityRequirement(name = "Bearer Authentication")
    fun createSprint(@RequestBody sprintRequestDTO: SprintRequestDTO): SprintDTO =
        toDTO(sprintService.createSprint(sprintRequestDTO))

    @PutMapping
    @RequirePermission(Permissions.MANAGE_SPRINT)
    @Operation(
        summary = "Update scrum sprint",
        description = "This operation requires MANAGE_SPRINT permission",
    )
    @SecurityRequirement(name = "Bearer Authentication")
    fun updateSprint(@RequestBody sprintInfo: SprintInfo): SprintDTO =
        toDTO(sprintService.updateSprint(sprintInfo))

    @DeleteMapping
    @RequirePermission(Permissions.MANAGE_SPRINT)
    @Operation(
        summary = "Delete scrum sprint",
        description = "This operation requires MANAGE_SPRINT permission",
    )
    @SecurityRequirement(name = "Bearer Authentication")
    fun deleteSprint(@RequestBody sprintInfo: SprintInfo): Unit =
        sprintService.deleteSprint(sprintInfo)

    @PutMapping("{sprintId}/toEpic/{epicId}")
    @RequirePermission(Permissions.MANAGE_SPRINT)
    @Operation(
        summary = "Link scrum sprint to scrum epic",
        description = "This operation requires MANAGE_SPRINT permission",
    )
    @SecurityRequirement(name = "Bearer Authentication")
    fun linkSprintToEpic(@PathVariable sprintId: Long, @PathVariable epicId: Long): SprintDTO =
        toDTO(sprintService.linkSprintToEpic(sprintId, epicId))

    @PutMapping("{sprintId}/toIssue/{issueId}")
    @RequirePermission(Permissions.MANAGE_SPRINT)
    @Operation(
        summary = "Link scrum sprint to issue",
        description = "This operation requires MANAGE_SPRINT permission",
    )
    @SecurityRequirement(name = "Bearer Authentication")
    fun linkSprintToIssue(@PathVariable sprintId: Long, @PathVariable issueId: Long): SprintDTO =
        toDTO(sprintService.linkSprintToIssue(sprintId, issueId))

    @DeleteMapping("{sprintId}/fromIssue/{issueId}")
    @RequirePermission(Permissions.MANAGE_SPRINT)
    @Operation(
        summary = "Unlink scrum sprint from issue",
        description = "This operation requires MANAGE_SPRINT permission",
    )
    @SecurityRequirement(name = "Bearer Authentication")
    fun unlinkSprintFromIssue(@PathVariable sprintId: Long, @PathVariable issueId: Long): SprintDTO =
        toDTO(sprintService.unlinkSprintFromIssue(sprintId, issueId))
}
