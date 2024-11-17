package io.github.zerumi.is_coursework_291024_klsp_bugtracker.service

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.IssueInfo
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.IssueRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.*
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository.*
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class IssueService(
    val userService: UserService,
    val sprintRepository: SprintRepository,
    val tagRepository: TagRepository,
    val eventRepository: EventRepository,
    val issueRepository: IssueRepository,
    val commentRepository: CommentRepository,
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

    fun linkIssueToSprint(issueId: Long, sprintId: Long): Issue {
        val issueToLink = issueRepository.getReferenceById(issueId)
        val sprintToLink = sprintRepository.getReferenceById(sprintId)

        issueToLink.relatedSprint?.issues?.remove(issueToLink)
        issueToLink.relatedSprint = sprintToLink
        sprintToLink.issues.add(issueToLink)

        return issueRepository.save(issueToLink)
    }

    fun linkSubIssueToIssue(subIssueId: Long, issueId: Long): Issue {
        val issueToLink = issueRepository.getReferenceById(subIssueId)
        val issueToAddSubIssue = issueRepository.getReferenceById(issueId)

        issueToLink.parentIssue?.subIssues?.remove(issueToLink)
        issueToLink.parentIssue = issueToAddSubIssue
        issueToAddSubIssue.subIssues.add(issueToLink)

        return issueRepository.save(issueToLink)
    }

    fun linkIssueToTag(issueId: Long, tagId: Long): Issue {
        val issueToAddTag = issueRepository.getReferenceById(issueId)
        val tagToAddToIssue = tagRepository.getReferenceById(tagId)

        issueToAddTag.tags.add(tagToAddToIssue)
        tagToAddToIssue.relatedIssues.add(issueToAddTag)

        return issueRepository.save(issueToAddTag)
    }

    fun linkIssueToEvent(issueId: Long, eventId: Long): Issue {
        val issueToAddEvent = issueRepository.getReferenceById(issueId)
        val eventToAddToIssue = eventRepository.getReferenceById(eventId)

        issueToAddEvent.events.add(eventToAddToIssue)
        eventToAddToIssue.issues.add(issueToAddEvent)

        return issueRepository.save(issueToAddEvent)
    }

    fun unlinkIssueWithSprint(issueId: Long): Issue {
        val issueToUnlink = issueRepository.getReferenceById(issueId)
        val sprintToUnlink = issueToUnlink.relatedSprint

        sprintToUnlink?.issues?.remove(issueToUnlink)
        issueToUnlink.relatedSprint = null

        return issueRepository.save(issueToUnlink)
    }

    fun unlinkSubIssueWithIssue(subIssueId: Long): Issue {
        val issueToUnlink = issueRepository.getReferenceById(subIssueId)
        val parentIssue = issueToUnlink.parentIssue

        parentIssue?.subIssues?.remove(issueToUnlink)
        issueToUnlink.parentIssue = null

        return issueRepository.save(issueToUnlink)
    }

    fun unlinkIssueWithTag(issueId: Long, tagId: Long): Issue {
        val issueToRemoveTag = issueRepository.getReferenceById(issueId)
        val tagToRemoveFromIssue = tagRepository.getReferenceById(tagId)

        issueToRemoveTag.tags.remove(tagToRemoveFromIssue)
        tagToRemoveFromIssue.relatedIssues.remove(issueToRemoveTag)

        return issueRepository.save(issueToRemoveTag)
    }

    fun unlinkIssueWithEvent(issueId: Long, eventId: Long): Issue {
        val issueToRemoveEvent = issueRepository.getReferenceById(issueId)
        val eventToRemoveIssue = eventRepository.getReferenceById(eventId)

        issueToRemoveEvent.events.remove(eventToRemoveIssue)
        eventToRemoveIssue.issues.remove(issueToRemoveEvent)

        return issueRepository.save(issueToRemoveEvent)
    }
}
