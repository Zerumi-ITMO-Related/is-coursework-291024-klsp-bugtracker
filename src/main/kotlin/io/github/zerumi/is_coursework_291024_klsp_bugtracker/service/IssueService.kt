package io.github.zerumi.is_coursework_291024_klsp_bugtracker.service

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.IssueDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.IssueRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.*
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository.CommentRepository
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository.IssueRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class IssueService(
    val userService: UserService, val issueRepository: IssueRepository, val commentRepository: CommentRepository
) {
    // todo sortProperty enum
    fun getIssues(pageNumber: Int, issuesPerPage: Int, sortProperty: String = "comment.creationTime"): List<Issue> =
        issueRepository.findAll(PageRequest.of(pageNumber, issuesPerPage, Sort.by(sortProperty))).toList()

    fun openIssue(issueModelDTO: IssueRequestDTO) {
        val comment = Comment(
            user = userService.getCurrentUser(),
            creationTime = ZonedDateTime.now(),
            lastModified = null,
            parentComment = null,
            content = issueModelDTO.content,
            attachedFiles = mutableListOf(), // todo filesystem
        )

        val newIssue = Issue(
            title = issueModelDTO.title,
            comment = comment,
            relatedSprint = null,
            parentIssue = null,
        )

        commentRepository.save(comment)
        issueRepository.save(newIssue)
    }

    fun updateIssue(issueDTO: IssueDTO) {
        requireNotNull(issueDTO.id)

        val currentUser = userService.getCurrentUser()
        val issueToUpdate = issueRepository.getReferenceById(issueDTO.id)

        requireOwnership(currentUser.id ?: -1, issueToUpdate.comment.user.id ?: -2) {
            requirePermissions(
                currentUser.permissionSet.permissions, Permissions.EDIT_ANY_ISSUE or Permissions.PRIVILEGED
            )
        }

        issueToUpdate.title = issueDTO.title

        issueRepository.save(issueToUpdate)
    }

    fun deleteIssue(id: Long) {
        val currentUser = userService.getCurrentUser()
        val issueToUpdate = issueRepository.getReferenceById(id)

        requireOwnership(currentUser.id ?: -1, issueToUpdate.comment.user.id ?: -2) {
            requirePermissions(
                currentUser.permissionSet.permissions, Permissions.EDIT_ANY_ISSUE or Permissions.PRIVILEGED
            )
        }

        issueRepository.deleteById(id)
    }
}
