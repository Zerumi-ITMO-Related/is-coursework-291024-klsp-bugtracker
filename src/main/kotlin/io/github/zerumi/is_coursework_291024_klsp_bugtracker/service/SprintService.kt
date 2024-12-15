package io.github.zerumi.is_coursework_291024_klsp_bugtracker.service

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.SprintInfo
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.SprintRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Sprint
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository.EpicRepository
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository.IssueRepository
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository.SprintRepository
import org.springframework.dao.CannotAcquireLockException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime

@Service
class SprintService(
    val epicService: EpicService,
    val sprintRepository: SprintRepository,
    val epicRepository: EpicRepository,
    val issueRepository: IssueRepository,
) {
    fun getSprints(pageNo: Int, epicsPerPage: Int, sortProperty: String = "createdAt"): List<Sprint> =
        sprintRepository.findAll(PageRequest.of(pageNo, epicsPerPage, Sort.by(sortProperty))).toList()

    fun getById(id: Long?) = sprintRepository.getReferenceById(requireNotNull(id))

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = [CannotAcquireLockException::class])
    fun createSprint(sprintRequestDTO: SprintRequestDTO): Sprint {
        val sprint = Sprint(
            name = sprintRequestDTO.name,
            description = sprintRequestDTO.description,
            createdAt = ZonedDateTime.now(),
            deadline = sprintRequestDTO.deadline,
            relatedEpic = epicService.getById(sprintRequestDTO.relatedEpicId),
        )

        return sprintRepository.save(sprint)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = [CannotAcquireLockException::class])
    fun updateSprint(sprintInfo: SprintInfo): Sprint {
        val sprintToUpdate = sprintRepository.getReferenceById(requireNotNull(sprintInfo.id))

        sprintToUpdate.name = sprintInfo.name
        sprintToUpdate.description = sprintInfo.description
        sprintToUpdate.deadline = sprintInfo.deadline

        return sprintRepository.save(sprintToUpdate)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = [CannotAcquireLockException::class])
    fun deleteSprint(sprintInfo: SprintInfo) = sprintRepository.deleteById(requireNotNull(sprintInfo.id))

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = [CannotAcquireLockException::class])
    fun linkSprintToEpic(sprintId: Long, epicId: Long): Sprint {
        val sprintToLinkWithEpic = sprintRepository.getReferenceById(sprintId)
        val epicToLinkWithSprint = epicRepository.getReferenceById(epicId)

        sprintToLinkWithEpic.relatedEpic.sprints.remove(sprintToLinkWithEpic)
        sprintToLinkWithEpic.relatedEpic = epicToLinkWithSprint
        epicToLinkWithSprint.sprints.add(sprintToLinkWithEpic)

        return sprintRepository.save(sprintToLinkWithEpic)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = [CannotAcquireLockException::class])
    fun linkSprintToIssue(sprintId: Long, issueId: Long): Sprint {
        val sprintToLinkWithIssue = sprintRepository.getReferenceById(sprintId)
        val issueToLinkWithSprint = issueRepository.getReferenceById(issueId)

        sprintToLinkWithIssue.issues.add(issueToLinkWithSprint)
        issueToLinkWithSprint.relatedSprint = sprintToLinkWithIssue

        return sprintRepository.save(sprintToLinkWithIssue)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = [CannotAcquireLockException::class])
    fun unlinkSprintFromIssue(sprintId: Long, issueId: Long): Sprint {
        val sprintToUnlinkWithIssue = sprintRepository.getReferenceById(sprintId)
        val issueToUnlinkWithSprint = issueRepository.getReferenceById(issueId)

        if (sprintToUnlinkWithIssue.issues.remove(issueToUnlinkWithSprint))
            issueToUnlinkWithSprint.relatedSprint = null

        return sprintRepository.save(sprintToUnlinkWithIssue)
    }
}
