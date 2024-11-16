package io.github.zerumi.is_coursework_291024_klsp_bugtracker.service

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.IssueInfo
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
    fun getById(id: Long?): Issue = issueRepository.getReferenceById(requireNotNull(id))

    // todo sortProperty enum
    fun getIssues(pageNumber: Int, issuesPerPage: Int, sortProperty: String = "comment.creationTime"): List<Issue> =
        issueRepository.findAll(PageRequest.of(pageNumber, issuesPerPage, Sort.by(sortProperty))).toList()

    fun createIssue(issueModelDTO: IssueRequestDTO): Issue {
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
        return issueRepository.save(newIssue)
    }

    fun updateIssue(issueInfo: IssueInfo): Issue {
        val issueToUpdate = issueRepository.getReferenceById(requireNotNull(issueInfo.id))

        issueToUpdate.title = issueInfo.title

        return issueRepository.save(issueToUpdate)
    }

    fun deleteIssue(issueInfo: IssueInfo) = issueRepository.deleteById(requireNotNull(issueInfo.id))
}
