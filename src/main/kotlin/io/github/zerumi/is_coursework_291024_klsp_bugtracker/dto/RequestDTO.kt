package io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.RatingValue
import java.io.Serializable

data class AuthRequestDTO(val login: String, val password: String) : Serializable

data class RegisterRequestDTO(val username: String, val displayName: String, val password: String)

data class IssueRequestDTO(
    val title: String, val content: String, val attachedFilesIds: List<Long>
) : Serializable

data class CommentRequestDTO(
    val parentCommentId: Long,
    val content: String,
    val attachedFilesIds: List<Long>,
) : Serializable

data class RatingRequestDTO(
    val commentId: Long,
    val rating: RatingValue
)
