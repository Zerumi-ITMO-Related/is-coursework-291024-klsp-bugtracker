package io.github.zerumi.is_coursework_291024_klsp_bugtracker.controller

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.annotation.RequirePermission
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.CommentDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.CommentInfo
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.CommentRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.toDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Permissions
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.service.CommentService
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
@RequestMapping("/api/v1/comment")
class CommentController(
    val commentService: CommentService
) {
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): CommentDTO {
        return toDTO(commentService.getById(id))
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer Authentication")
    fun createReply(@RequestBody commentRequestDTO: CommentRequestDTO) {
        return commentService.createComment(commentRequestDTO)
    }

    @PutMapping
    @PreAuthorize("hasPermission(#comment, 'CommentInfo', 'UPDATE_ANY_COMMENT')")
    @SecurityRequirement(name = "Bearer Authentication")
    fun updateComment(@RequestBody @P("comment") commentInfo: CommentInfo) {
        return commentService.updateComment(commentInfo)
    }

    @DeleteMapping
    @PreAuthorize("hasPermission(#comment, 'CommentInfo', 'UPDATE_ANY_COMMENT')")
    @SecurityRequirement(name = "Bearer Authentication")
    fun deleteComment(@RequestBody commentInfo: CommentInfo) {
        return commentService.deleteComment(commentInfo)
    }
}
