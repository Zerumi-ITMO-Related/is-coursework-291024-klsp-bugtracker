package io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.context
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.*
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.security.OwnedObject
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.service.CommentService
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.service.IssueService
import java.io.Serializable
import java.time.ZonedDateTime

data class CommentInfo(
    val id: Long? = null,
    val creationTime: ZonedDateTime,
    val lastModified: ZonedDateTime?,
    val content: String,
) : Serializable, OwnedObject {
    override fun ownership(): User = toObject(this).user
}

data class EpicInfo(
    val id: Long? = null,
    val name: String,
    val description: String,
    val deadline: ZonedDateTime,
) : Serializable

data class EventInfo(
    val id: Long? = null,
    val name: String,
    val description: String,
    val date: ZonedDateTime,
    val eventReview: String?,
) : Serializable

data class FileInfo(
    val id: Long? = null,
    val filepath: String,
    val mimeType: String,
    val uploadTime: ZonedDateTime,
) : Serializable

data class IssueInfo(
    val id: Long? = null,
    val title: String,
) : Serializable, OwnedObject {
    override fun ownership(): User = toObject(this).comment.user
}

data class PermissionSetInfo(
    val id: Long? = null,
    val name: String,
    val permissions: ULong,
) : Serializable

data class RatingInfo(
    val id: Long? = null,
    val rating: RatingValue,
) : Serializable

data class SprintInfo(
    val id: Long? = null,
    val name: String,
    val description: String,
    val createdAt: ZonedDateTime,
    val deadline: ZonedDateTime,
) : Serializable

data class TagInfo(
    val id: Long? = null,
    val name: String,
    val color: String,
) : Serializable

data class UserInfo(
    val id: Long? = null,
    val displayName: String,
) : Serializable

fun toInfo(comment: Comment): CommentInfo = CommentInfo(
    id = comment.id,
    creationTime = comment.creationTime,
    lastModified = comment.lastModified,
    content = comment.content,
)

fun toInfo(epic: Epic): EpicInfo = EpicInfo(
    id = epic.id,
    name = epic.name,
    description = epic.description,
    deadline = epic.deadline,
)

fun toInfo(event: Event): EventInfo = EventInfo(
    id = event.id,
    name = event.name,
    description = event.description,
    date = event.date,
    eventReview = event.eventReview,
)

fun toInfo(file: File): FileInfo = FileInfo(
    id = file.id,
    filepath = file.filepath,
    mimeType = file.mimeType,
    uploadTime = file.uploadTime,
)

fun toInfo(issue: Issue): IssueInfo = IssueInfo(
    id = issue.id,
    title = issue.title,
)

fun toInfo(permissionSet: PermissionSet): PermissionSetInfo = PermissionSetInfo(
    id = permissionSet.id,
    name = permissionSet.name,
    permissions = permissionSet.permissions,
)

fun toInfo(rating: Rating): RatingInfo = RatingInfo(
    id = rating.id,
    rating = rating.rating,
)

fun toInfo(sprint: Sprint): SprintInfo = SprintInfo(
    id = sprint.id,
    name = sprint.name,
    description = sprint.description,
    createdAt = sprint.createdAt,
    deadline = sprint.deadline,
)

fun toInfo(tag: Tag): TagInfo = TagInfo(
    id = tag.id,
    name = tag.name,
    color = tag.color,
)

fun toInfo(user: User): UserInfo = UserInfo(
    id = user.id,
    displayName = user.displayName,
)

fun toObject(issueInfo: IssueInfo) = context.getBean(IssueService::class.java).getById(issueInfo.id)
