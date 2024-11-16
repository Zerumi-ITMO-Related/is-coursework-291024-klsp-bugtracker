package io.github.zerumi.is_coursework_291024_klsp_bugtracker.controller

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.annotation.RequirePermission
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.*
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Permissions
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.service.EpicService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.parameters.P
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/epic")
class EpicController(
    val epicService: EpicService,
) {
    @GetMapping("/{id}")
    fun getEpic(@PathVariable id: Long): EpicDTO {
        return toDTO(epicService.getById(id))
    }

    @GetMapping("/page/{pageNo}/{epicsPerPage}")
    fun getEpics(@PathVariable pageNo: Int, @PathVariable epicsPerPage: Int): List<EpicDTO> {
        val issues = epicService.getEpics(pageNo, epicsPerPage)
        return issues.map { toDTO(it) }
    }

    @PostMapping
    @RequirePermission(Permissions.MANAGE_EPIC)
    @SecurityRequirement(name = "Bearer Authentication")
    fun createEpic(@RequestBody epicRequestDTO: EpicRequestDTO) {
        epicService.createEpic(epicRequestDTO)
    }

    @PutMapping
    @RequirePermission(Permissions.MANAGE_EPIC)
    @SecurityRequirement(name = "Bearer Authentication")
    fun updateEpic(@RequestBody @P("epic") epicInfo: EpicInfo) {
        epicService.updateEpic(epicInfo)
    }

    @DeleteMapping
    @RequirePermission(Permissions.MANAGE_EPIC)
    @SecurityRequirement(name = "Bearer Authentication")
    fun deleteEpic(@RequestBody @P("epic") epicInfo: EpicInfo) {
        epicService.deleteEpic(epicInfo)
    }
}