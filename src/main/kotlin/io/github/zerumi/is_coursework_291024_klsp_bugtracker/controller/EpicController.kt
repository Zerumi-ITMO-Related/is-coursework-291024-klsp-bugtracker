package io.github.zerumi.is_coursework_291024_klsp_bugtracker.controller

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.annotation.RequirePermission
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.EpicDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.EpicInfo
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.EpicRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.toDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Permissions
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.service.EpicService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/epic")
@Tag(name = "Scrum Epic API", description = "Endpoints for managing epics")
class EpicController(
    val epicService: EpicService,
) {
    @GetMapping("/{id}")
    @Operation(summary = "Get an epic by its id")
    fun getEpic(@PathVariable id: Long): EpicDTO =
        toDTO(epicService.getById(id))

    @GetMapping("/page/{pageNo}/{epicsPerPage}")
    @Operation(summary = "Get epics in paged view")
    fun getEpics(@PathVariable pageNo: Int, @PathVariable epicsPerPage: Int): List<EpicDTO> =
        epicService.getEpics(pageNo, epicsPerPage).map { toDTO(it) }

    @PostMapping
    @RequirePermission(Permissions.MANAGE_EPIC)
    @Operation(
        summary = "Create an epic",
        description = "This operation requires MANAGE_EPIC permission"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    fun createEpic(@RequestBody epicRequestDTO: EpicRequestDTO): EpicDTO =
        toDTO(epicService.createEpic(epicRequestDTO))

    @PutMapping
    @RequirePermission(Permissions.MANAGE_EPIC)
    @Operation(
        summary = "Update an epic",
        description = "This operation requires MANAGE_EPIC permission"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    fun updateEpic(@RequestBody epicInfo: EpicInfo): EpicDTO =
        toDTO(epicService.updateEpic(epicInfo))

    @DeleteMapping
    @RequirePermission(Permissions.MANAGE_EPIC)
    @Operation(
        summary = "Delete an epic",
        description = "This operation requires MANAGE_EPIC permission"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    fun deleteEpic(@RequestBody epicInfo: EpicInfo): Unit =
        epicService.deleteEpic(epicInfo)
}
