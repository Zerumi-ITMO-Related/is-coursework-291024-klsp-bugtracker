package io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.*
import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable
import java.time.ZonedDateTime

@Schema(
    name = "Comment Data object",
    description = "This is a standard entity data object." +
            "To avoid recursive mapping, all data objects contains a reference to serializable info objects"
)
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

@Schema(
    name = "Scrum epic Data object",
    description = "This is a standard entity data object." +
            "To avoid recursive mapping, all data objects contains a reference to serializable info objects"
)
data class EpicDTO(
    val id: Long? = null, val name: String, val description: String, val deadline: ZonedDateTime,

    val sprints: Collection<SprintInfo> = mutableListOf()
) : Serializable

@Schema(
    name = "Scrum event Data object",
    description = "This is a standard entity data object." +
            "To avoid recursive mapping, all data objects contains a reference to serializable info objects"
)
data class EventDTO(
    val id: Long? = null,
    val name: String,
    val description: String,
    val date: ZonedDateTime,
    val eventReview: String?,
    val issues: Collection<IssueInfo> = mutableListOf(),
) : Serializable

@Schema(
    name = "File Data object",
    description = "This is a standard entity data object." +
            "To avoid recursive mapping, all data objects contains a reference to serializable info objects"
)
data class FileDTO(
    val id: Long? = null,
    val filepath: String,
    val mimeType: String,
    val uploadTime: ZonedDateTime,
    val relatedComment: CommentInfo? = null
) : Serializable

@Schema(
    name = "Issue Data object",
    description = "This is a standard entity data object." +
            "To avoid recursive mapping, all data objects contains a reference to serializable info objects"
)
data class IssueDTO(
    val id: Long? = null,
    val title: String,
    val ratio: Double,
    val comment: CommentInfo,
    val relatedSprint: SprintInfo?,
    val parentIssue: IssueInfo?,
    val tags: Collection<TagInfo> = mutableListOf(),

    val events: Collection<EventInfo> = mutableListOf(),
    val subIssues: Collection<IssueInfo> = mutableListOf(),
) : Serializable

@Schema(
    name = "Permission set Data object",
    description = "This is a standard entity data object." +
            "To avoid recursive mapping, all data objects contains a reference to serializable info objects"
)
data class PermissionSetDTO(
    val id: Long? = null,
    val name: String,
    val assignedUsers: Collection<UserInfo> = mutableListOf(),
    @Schema(
        description = "This ULong represents binary sequence of permissions. " +
                "All permissions are checking by binary and/or operations with required bitmask"
    )
    val permissions: ULong,
) : Serializable

@Schema(
    name = "Comment rating Data object",
    description = "This is a standard entity data object." +
            "To avoid recursive mapping, all data objects contains a reference to serializable info objects"
)
data class RatingDTO(
    val id: Long? = null,
    val comment: CommentInfo,
    val user: UserInfo,
    val rating: RatingValue,
) : Serializable

@Schema(
    name = "Scrum sprint Data object",
    description = "This is a standard entity data object." +
            "To avoid recursive mapping, all data objects contains a reference to serializable info objects"
)
data class SprintDTO(
    val id: Long? = null,
    val name: String,
    val description: String,
    val createdAt: ZonedDateTime,
    val deadline: ZonedDateTime,
    val relatedEpic: EpicInfo,

    val issues: Collection<IssueInfo> = mutableListOf()
) : Serializable

@Schema(
    name = "Issue tag Data object",
    description = "This is a standard entity data object." +
            "To avoid recursive mapping, all data objects contains a reference to serializable info objects"
)
data class TagDTO(
    val id: Long? = null,
    val name: String,
    @Schema(
        description = "For now it's unspecified how color should be stored, and we assume it " +
                "will be depend on the end implementation"
    )
    val color: String,
    val relatedIssues: Collection<IssueInfo> = mutableListOf()
) : Serializable

@Schema(
    name = "User Data object",
    description = "This is a standard entity data object." +
            "To avoid recursive mapping, all data objects contains a reference to serializable info objects"
)
data class UserDTO(
    val id: Long? = null,
    val displayName: String,
    val ratio: Double,
    val avatar: FileInfo? = null,

    val permissionSet: PermissionSetInfo,
    val comments: Collection<CommentInfo> = mutableListOf(),
    val ratings: Collection<RatingInfo> = mutableListOf(),
) : Serializable

fun toDTO(issue: Issue): IssueDTO = IssueDTO(
    id = issue.id,
    title = issue.title,
    comment = toInfo(issue.comment),
    ratio = issue.ratio,
    relatedSprint = issue.relatedSprint?.let { toInfo(it) },
    parentIssue = issue.parentIssue?.let { toInfo(it) },
    tags = issue.tags.map { toInfo(it) },
    events = issue.events.map { toInfo(it) },
    subIssues = issue.subIssues.map { toInfo(it) }
)

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

fun toDTO(user: User): UserDTO = UserDTO(
    id = user.id,
    displayName = user.displayName,
    ratio = user.ratio,
    avatar = user.avatar?.let { toInfo(it) },
    comments = user.comments.map { toInfo(it) },
    ratings = user.ratings.map { toInfo(it) },
    permissionSet = toInfo(user.permissionSet)
)

fun toDTO(file: File): FileDTO = FileDTO(
    id = file.id,
    filepath = file.filepath,
    mimeType = file.mimeType,
    uploadTime = file.uploadTime
)

fun toDTO(rating: Rating): RatingDTO = RatingDTO(
    id = rating.id,
    user = toInfo(rating.user),
    rating = rating.rating,
    comment = toInfo(rating.comment)
)

fun toDTO(epic: Epic) = EpicDTO(
    id = epic.id,
    name = epic.name,
    description = epic.description,
    deadline = epic.deadline,
    sprints = epic.sprints.map { toInfo(it) }
)

fun toDTO(sprint: Sprint) = SprintDTO(
    id = sprint.id,
    name = sprint.name,
    description = sprint.description,
    createdAt = sprint.createdAt,
    deadline = sprint.deadline,
    relatedEpic = toInfo(sprint.relatedEpic),
    issues = sprint.issues.map { toInfo(it) }
)

fun toDTO(event: Event): EventDTO = EventDTO(
    id = event.id,
    name = event.name,
    description = event.description,
    date = event.date,
    eventReview = event.eventReview,
    issues = event.issues.map { toInfo(it) }
)

fun toDTO(tag: Tag): TagDTO = TagDTO(
    id = tag.id,
    name = tag.name,
    color = tag.color,
    relatedIssues = tag.relatedIssues.map { toInfo(it) }
)

fun toDTO(permissionSet: PermissionSet): PermissionSetDTO = PermissionSetDTO(
    id = permissionSet.id,
    name = permissionSet.name,
    assignedUsers = permissionSet.assignedUsers.map { toInfo(it) },
    permissions = permissionSet.permissions
)
