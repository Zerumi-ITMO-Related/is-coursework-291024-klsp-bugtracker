package io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.*
import java.io.Serializable
import java.time.ZonedDateTime

data class CommentDTO(
    val id: Long? = null,
    val user: UserInfo,
    val creationTime: ZonedDateTime,
    val lastModified: ZonedDateTime?,
    val parentComment: CommentInfo?,
    val content: String,

    val subThread: Collection<CommentInfo> = mutableListOf(),
    val attachedFiles: Collection<FileInfo> = mutableListOf(),
    val ratings: Collection<RatingInfo> = mutableListOf(),
) : Serializable

data class EpicDTO(
    val id: Long? = null, val name: String, val description: String, val deadline: ZonedDateTime,

    val sprints: Collection<SprintInfo> = mutableListOf()
) : Serializable

data class EventDTO(
    val id: Long? = null,
    val name: String,
    val description: String,
    val date: ZonedDateTime,
    val eventReview: String?,
    val issues: Collection<IssueInfo> = mutableListOf(),
) : Serializable

data class FileDTO(
    val id: Long? = null,
    val filepath: String,
    val mimeType: String,
    val uploadTime: ZonedDateTime,
    val relatedComment: CommentInfo? = null
) : Serializable

data class IssueDTO(
    val id: Long? = null,
    val title: String,
    val comment: CommentInfo,
    val relatedSprint: SprintInfo?,
    val parentIssue: IssueInfo?,
    val tags: Collection<TagInfo> = mutableListOf(),

    val events: Collection<EventInfo> = mutableListOf(),
    val subIssues: Collection<IssueInfo> = mutableListOf(),
) : Serializable

data class PermissionSetDTO(
    val id: Long? = null,
    val name: String,
    val assignedUsers: Collection<UserInfo> = mutableListOf(),
    val permissions: ULong,
) : Serializable

data class RatingDTO(
    val id: Long? = null,
    val comment: CommentInfo,
    val user: UserInfo,
    val rating: RatingValue,
) : Serializable

data class SprintDTO(
    val id: Long? = null,
    val name: String,
    val description: String,
    val createdAt: ZonedDateTime,
    val deadline: ZonedDateTime,
    val relatedEpic: EpicInfo,

    val issues: Collection<IssueInfo> = mutableListOf()
) : Serializable

data class TagDTO(
    val id: Long? = null,
    val name: String,
    val color: String,
    val relatedIssues: Collection<IssueInfo> = mutableListOf()
) : Serializable

data class UserDTO(
    val id: Long? = null,
    val displayName: String,
    val avatar: FileInfo? = null,

    val comments: Collection<CommentInfo> = mutableListOf(),
    val ratings: Collection<RatingInfo> = mutableListOf(),
) : Serializable

fun toDTO(issue: Issue): IssueDTO = IssueDTO(id = issue.id,
    title = issue.title,
    comment = toInfo(issue.comment),
    relatedSprint = issue.relatedSprint?.let { toInfo(it) },
    parentIssue = issue.parentIssue?.let { toInfo(it) },
    tags = issue.tags.map { toInfo(it) },
    events = issue.events.map { toInfo(it) },
    subIssues = issue.subIssues.map { toInfo(it) })

fun toDTO(comment: Comment): CommentDTO = CommentDTO(
    id = comment.id,
    user = toInfo(comment.user),
    creationTime = comment.creationTime,
    lastModified = comment.lastModified,
    parentComment = comment.parentComment?.let { toInfo(it) },
    content = comment.content,
    subThread = comment.subThread.map { toInfo(it) },
    attachedFiles = comment.attachedFiles.map { toInfo(it) },
    ratings = comment.ratings.map { toInfo(it) },
)

fun toDTO(user: User) = UserDTO(id = user.id,
    displayName = user.displayName,
    avatar = user.avatar?.let { toInfo(it) },
    comments = user.comments.map { toInfo(it) },
    ratings = user.ratings.map { toInfo(it) })

fun toDTO(file: File) = FileDTO(
    id = file.id, filepath = file.filepath, mimeType = file.mimeType, uploadTime = file.uploadTime
)

fun toDTO(rating: Rating) = RatingDTO(
    id = rating.id, user = toInfo(rating.user), rating = rating.rating, comment = toInfo(rating.comment)
)
