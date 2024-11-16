package io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.RatingValue
import java.io.Serializable
import java.time.ZonedDateTime

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
) : Serializable

data class EpicRequestDTO(
    val name: String,
    val description: String,
    val deadline: ZonedDateTime
) : Serializable

data class SprintRequestDTO(
    val name: String,
    val description: String,
    val deadline: ZonedDateTime,
    val relatedEpicId: Long,
) : Serializable

data class EventRequestDTO(
    val name: String,
    val description: String,
    val date: ZonedDateTime,
) : Serializable

data class TagRequestDTO(
    val name: String,
    val color: String,
) : Serializable
