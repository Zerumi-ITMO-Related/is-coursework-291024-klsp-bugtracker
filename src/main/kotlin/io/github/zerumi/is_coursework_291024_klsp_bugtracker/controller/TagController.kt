package io.github.zerumi.is_coursework_291024_klsp_bugtracker.controller

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.annotation.RequirePermission
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.*
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Permissions
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.service.TagService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/tag")
@Tag(name = "Issue Tag API", description = "Endpoints for managing issue tags")
class TagController(
    val tagService: TagService
) {
    @GetMapping("/page/{pageNo}/{tagsPerPage}")
    @Operation(summary = "Get tags in paged view")
    fun getTags(@PathVariable pageNo: Int, @PathVariable epicsPerPage: Int): List<TagDTO> =
        tagService.getTags(pageNo, epicsPerPage).map { toDTO(it) }

    @GetMapping("/{id}")
    @Operation(summary = "Get an issue tag by its id")
    fun getTag(@PathVariable id: Long): TagDTO = toDTO(tagService.getById(id))

    @PostMapping
    @RequirePermission(Permissions.MANAGE_TAG)
    @Operation(
        summary = "Create issue tag",
        description = "This operation requires MANAGE_TAG permission",
    )
    @SecurityRequirement(name = "Bearer Authentication")
    fun createTag(@RequestBody tagRequestDTO: TagRequestDTO): TagDTO = toDTO(tagService.createTag(tagRequestDTO))

    @PutMapping
    @RequirePermission(Permissions.MANAGE_TAG)
    @Operation(
        summary = "Update issue tag",
        description = "This operation requires MANAGE_TAG permission",
    )
    @SecurityRequirement(name = "Bearer Authentication")
    fun updateTag(@RequestBody tagInfo: TagInfo): TagDTO = toDTO(tagService.updateTag(tagInfo))

    @DeleteMapping
    @RequirePermission(Permissions.MANAGE_TAG)
    @Operation(
        summary = "Delete issue tag",
        description = "This operation requires MANAGE_TAG permission",
    )
    @SecurityRequirement(name = "Bearer Authentication")
    fun deleteTag(@RequestBody tagInfo: TagInfo): Unit = tagService.deleteTag(tagInfo)
}
