package io.github.zerumi.is_coursework_291024_klsp_bugtracker.controller

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.annotation.RequirePermission
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.TagDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.TagInfo
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.TagRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.toDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Permissions
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.service.TagService
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
@RequestMapping("/api/v1/tag")
class TagController(
    val tagService: TagService
) {
    @GetMapping("/{id}")
    fun getTag(@PathVariable id: Long) : TagDTO {
        return toDTO(tagService.getById(id))
    }

    @PostMapping
    @RequirePermission(Permissions.MANAGE_TAGS)
    @SecurityRequirement(name = "Bearer Authentication")
    fun createTag(@RequestBody tagRequestDTO: TagRequestDTO) {
        return tagService.createTag(tagRequestDTO)
    }

    @PutMapping
    @RequirePermission(Permissions.MANAGE_TAGS)
    @SecurityRequirement(name = "Bearer Authentication")
    fun updateTag(@RequestBody tagInfo: TagInfo) {
        return tagService.updateTag(tagInfo)
    }

    @DeleteMapping
    @RequirePermission(Permissions.MANAGE_TAGS)
    @SecurityRequirement(name = "Bearer Authentication")
    fun deleteTag(@RequestBody tagInfo: TagInfo) {
        return tagService.deleteTag(tagInfo)
    }
}
