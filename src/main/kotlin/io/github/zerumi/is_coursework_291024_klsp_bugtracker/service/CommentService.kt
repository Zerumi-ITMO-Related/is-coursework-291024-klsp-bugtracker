package io.github.zerumi.is_coursework_291024_klsp_bugtracker.service

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.CommentInfo
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.CommentRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Comment
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository.CommentRepository
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class CommentService(
    val commentRepository: CommentRepository,
    val userService: UserService,
    val issueService: IssueService,
) {
    fun getById(id: Long?): Comment = commentRepository.getReferenceById(requireNotNull(id))

    fun createComment(commentRequestDTO: CommentRequestDTO): Comment {
        val parentComment = commentRepository.getReferenceById(commentRequestDTO.parentCommentId)

        val comment = Comment(
            user = userService.getCurrentUser(),
            creationTime = ZonedDateTime.now(),
            lastModified = null,
            parentComment = parentComment,
            content = commentRequestDTO.content,
        )

        if (parentComment.parentComment == null)
            issueService.increaseRatio(
                requireNotNull(issueService.issueRepository.findByComment(parentComment).id),
                IssueService.INCREASE_PER_COMMENT
            )

        return commentRepository.save(comment)
    }

    fun updateComment(commentInfo: CommentInfo): Comment {
        val commentToUpdate = commentRepository.getReferenceById(requireNotNull(commentInfo.id))

        commentToUpdate.content = commentInfo.content

        return commentRepository.save(commentToUpdate)
    }

    fun deleteComment(commentInfo: CommentInfo) = commentRepository.deleteById(requireNotNull(commentInfo.id))
}
