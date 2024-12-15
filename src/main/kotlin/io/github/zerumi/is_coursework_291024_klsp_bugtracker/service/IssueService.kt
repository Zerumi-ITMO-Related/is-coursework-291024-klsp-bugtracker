package io.github.zerumi.is_coursework_291024_klsp_bugtracker.service

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.IssueInfo
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.IssueRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.*
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository.*
import org.springframework.dao.CannotAcquireLockException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
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

    fun getIssues(pageNumber: Int, issuesPerPage: Int): List<Issue> =
        issueRepository.findByParentIssueIsNull(PageRequest.of(pageNumber, issuesPerPage, Sort.by("ratio").descending()))

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = [CannotAcquireLockException::class])
    fun createIssue(issueModelDTO: IssueRequestDTO): Issue {
        val comment = Comment(
            user = userService.getCurrentUser(),
            creationTime = ZonedDateTime.now(),
            lastModified = null,
            parentComment = null,
            content = issueModelDTO.content,
            attachedFiles = mutableListOf(),
        )

        val newIssue = Issue(
            title = issueModelDTO.title,
            ratio = userService.getCurrentUser().ratio,
            comment = comment,
            relatedSprint = null,
            parentIssue = null,
        )

        commentRepository.save(comment)
        return issueRepository.save(newIssue)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = [CannotAcquireLockException::class])
    fun updateIssue(issueInfo: IssueInfo): Issue {
        val issueToUpdate = issueRepository.getReferenceById(requireNotNull(issueInfo.id))

        issueToUpdate.title = issueInfo.title

        return issueRepository.save(issueToUpdate)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = [CannotAcquireLockException::class])
    fun deleteIssue(issueInfo: IssueInfo) = issueRepository.deleteById(requireNotNull(issueInfo.id))

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = [CannotAcquireLockException::class])
    fun linkIssueToSprint(issueId: Long, sprintId: Long): Issue {
        val issueToLink = issueRepository.getReferenceById(issueId)
        val sprintToLink = sprintRepository.getReferenceById(sprintId)

        issueToLink.relatedSprint?.issues?.remove(issueToLink)
        issueToLink.relatedSprint = sprintToLink
        sprintToLink.issues.add(issueToLink)

        return issueRepository.save(issueToLink)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = [CannotAcquireLockException::class])
    fun linkSubIssueToIssue(subIssueId: Long, issueId: Long): Issue {
        val issueToLink = issueRepository.getReferenceById(subIssueId)
        val issueToAddSubIssue = issueRepository.getReferenceById(issueId)

        issueToLink.parentIssue?.subIssues?.remove(issueToLink)
        issueToLink.parentIssue = issueToAddSubIssue
        issueToAddSubIssue.subIssues.add(issueToLink)

        return issueRepository.save(issueToLink)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = [CannotAcquireLockException::class])
    fun linkIssueToTag(issueId: Long, tagId: Long): Issue {
        val issueToAddTag = issueRepository.getReferenceById(issueId)
        val tagToAddToIssue = tagRepository.getReferenceById(tagId)

        issueToAddTag.tags.add(tagToAddToIssue)
        tagToAddToIssue.relatedIssues.add(issueToAddTag)

        return issueRepository.save(issueToAddTag)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = [CannotAcquireLockException::class])
    fun linkIssueToEvent(issueId: Long, eventId: Long): Issue {
        val issueToAddEvent = issueRepository.getReferenceById(issueId)
        val eventToAddToIssue = eventRepository.getReferenceById(eventId)

        issueToAddEvent.events.add(eventToAddToIssue)
        eventToAddToIssue.issues.add(issueToAddEvent)

        return issueRepository.save(issueToAddEvent)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = [CannotAcquireLockException::class])
    fun unlinkIssueFromSprint(issueId: Long): Issue {
        val issueToUnlink = issueRepository.getReferenceById(issueId)
        val sprintToUnlink = issueToUnlink.relatedSprint

        sprintToUnlink?.issues?.remove(issueToUnlink)
        issueToUnlink.relatedSprint = null

        return issueRepository.save(issueToUnlink)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = [CannotAcquireLockException::class])
    fun unlinkSubIssueFromIssue(subIssueId: Long): Issue {
        val issueToUnlink = issueRepository.getReferenceById(subIssueId)
        val parentIssue = issueToUnlink.parentIssue

        parentIssue?.subIssues?.remove(issueToUnlink)
        issueToUnlink.parentIssue = null

        return issueRepository.save(issueToUnlink)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = [CannotAcquireLockException::class])
    fun unlinkIssueFromTag(issueId: Long, tagId: Long): Issue {
        val issueToRemoveTag = issueRepository.getReferenceById(issueId)
        val tagToRemoveFromIssue = tagRepository.getReferenceById(tagId)

        issueToRemoveTag.tags.remove(tagToRemoveFromIssue)
        tagToRemoveFromIssue.relatedIssues.remove(issueToRemoveTag)

        return issueRepository.save(issueToRemoveTag)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = [CannotAcquireLockException::class])
    fun unlinkIssueFromEvent(issueId: Long, eventId: Long): Issue {
        val issueToRemoveEvent = issueRepository.getReferenceById(issueId)
        val eventToRemoveIssue = eventRepository.getReferenceById(eventId)

        issueToRemoveEvent.events.remove(eventToRemoveIssue)
        eventToRemoveIssue.issues.remove(issueToRemoveEvent)

        return issueRepository.save(issueToRemoveEvent)
    }

    @Scheduled(cron = "0 0 0 * * *")
    fun decreaseRatioEveryDay() {
        val issues = issueRepository.findAll()
        for (issue in issues) {
            issue.ratio = (issue.ratio + INCREASE_PER_DAY).coerceAtLeast(0.0)
        }
        issueRepository.saveAll(issues)
    }

    fun increaseRatio(issueId: Long, ratio: Double) {
        val issue = issueRepository.getReferenceById(issueId)
        issue.ratio += ratio
        issueRepository.save(issue)
    }

    companion object {
        const val INCREASE_PER_DAY = -5.0
        const val INCREASE_PER_LIKE = 5.0
        const val INCREASE_PER_DISLIKE = -3.0
        const val INCREASE_PER_COMMENT = 3.0
    }
}
