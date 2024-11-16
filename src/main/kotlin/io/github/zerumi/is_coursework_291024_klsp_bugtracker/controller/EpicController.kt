package io.github.zerumi.is_coursework_291024_klsp_bugtracker.controller

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.annotation.RequirePermission
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.EpicDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.EpicInfo
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.EpicRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.toDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Permissions
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.service.EpicService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/epic")
class EpicController(
    val epicService: EpicService,
) {
    @GetMapping("/{id}")
    fun getEpic(@PathVariable id: Long): EpicDTO =
        toDTO(epicService.getById(id))

    @GetMapping("/page/{pageNo}/{epicsPerPage}")
    fun getEpics(@PathVariable pageNo: Int, @PathVariable epicsPerPage: Int): List<EpicDTO> =
        epicService.getEpics(pageNo, epicsPerPage).map { toDTO(it) }

    @PostMapping
    @RequirePermission(Permissions.MANAGE_EPIC)
    @SecurityRequirement(name = "Bearer Authentication")
    fun createEpic(@RequestBody epicRequestDTO: EpicRequestDTO): EpicDTO =
        toDTO(epicService.createEpic(epicRequestDTO))

    @PutMapping
    @RequirePermission(Permissions.MANAGE_EPIC)
    @SecurityRequirement(name = "Bearer Authentication")
    fun updateEpic(@RequestBody epicInfo: EpicInfo): EpicDTO =
        toDTO(epicService.updateEpic(epicInfo))

    @DeleteMapping
    @RequirePermission(Permissions.MANAGE_EPIC)
    @SecurityRequirement(name = "Bearer Authentication")
    fun deleteEpic(@RequestBody epicInfo: EpicInfo): Unit =
        epicService.deleteEpic(epicInfo)
}
