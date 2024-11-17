package io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.RatingValue
import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable
import java.time.ZonedDateTime

@Schema(
    name = "Auth request payload",
    description = "This is a standard payload object for create some data. " +
            "The main difference between Info object is that ID field doesn't exists"
)
data class AuthRequestDTO(
    val login: String,
    val password: String,
) : Serializable

@Schema(
    name = "Register request payload",
    description = "This is a standard payload object for create some data. " +
            "The main difference between Info object is that ID field doesn't exists"
)
data class RegisterRequestDTO(
    val username: String,
    val displayName: String,
    val email: String,
    val password: String,
) : Serializable

@Schema(
    name = "Issue request payload",
    description = "This is a standard payload object for create some data. " +
            "The main difference between Info object is that ID field doesn't exists"
)
data class IssueRequestDTO(
    val title: String,
    val content: String,
    val attachedFilesIds: List<Long>,
) : Serializable

@Schema(
    name = "Comment request payload",
    description = "This is a standard payload object for create some data. " +
            "The main difference between Info object is that ID field doesn't exists"
)
data class CommentRequestDTO(
    val parentCommentId: Long,
    val content: String,
    val attachedFilesIds: List<Long>,
) : Serializable

@Schema(
    name = "Rating request payload",
    description = "This is a standard payload object for create some data. " +
            "The main difference between Info object is that ID field doesn't exists"
)
data class RatingRequestDTO(
    val commentId: Long,
    val rating: RatingValue,
) : Serializable

@Schema(
    name = "Epic request payload",
    description = "This is a standard payload object for create some data. " +
            "The main difference between Info object is that ID field doesn't exists"
)
data class EpicRequestDTO(
    val name: String,
    val description: String,
    val deadline: ZonedDateTime,
) : Serializable

@Schema(
    name = "Sprint request payload",
    description = "This is a standard payload object for create some data. " +
            "The main difference between Info object is that ID field doesn't exists"
)
data class SprintRequestDTO(
    val name: String,
    val description: String,
    val deadline: ZonedDateTime,
    val relatedEpicId: Long,
) : Serializable

@Schema(
    name = "Event request payload",
    description = "This is a standard payload object for create some data. " +
            "The main difference between Info object is that ID field doesn't exists"
)
data class EventRequestDTO(
    val name: String,
    val description: String,
    val date: ZonedDateTime,
) : Serializable

@Schema(
    name = "Tag request payload",
    description = "This is a standard payload object for create some data. " +
            "The main difference between Info object is that ID field doesn't exists"
)
data class TagRequestDTO(
    val name: String,
    val color: String,
) : Serializable

@Schema(
    name = "Permission request payload",
    description = "This is a standard payload object for create some data. " +
            "The main difference between Info object is that ID field doesn't exists"
)
data class PermissionSetRequestDTO(
    val name: String,
    val permissions: ULong,
) : Serializable
