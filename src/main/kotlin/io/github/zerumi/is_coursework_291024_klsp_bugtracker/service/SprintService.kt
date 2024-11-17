package io.github.zerumi.is_coursework_291024_klsp_bugtracker.service

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.SprintInfo
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.SprintRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Sprint
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository.EpicRepository
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository.IssueRepository
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository.SprintRepository
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class SprintService(
    val epicService: EpicService,
    val sprintRepository: SprintRepository,
    val epicRepository: EpicRepository,
    val issueRepository: IssueRepository,
) {
    fun getById(id: Long?) = sprintRepository.getReferenceById(requireNotNull(id))

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

    fun updateSprint(sprintInfo: SprintInfo): Sprint {
        val sprintToUpdate = sprintRepository.getReferenceById(requireNotNull(sprintInfo.id))

        sprintToUpdate.name = sprintInfo.name
        sprintToUpdate.description = sprintInfo.description
        sprintToUpdate.deadline = sprintInfo.deadline

        return sprintRepository.save(sprintToUpdate)
    }

    fun deleteSprint(sprintInfo: SprintInfo) = sprintRepository.deleteById(requireNotNull(sprintInfo.id))

    fun linkSprintToEpic(sprintId: Long, epicId: Long): Sprint {
        val sprintToLinkWithEpic = sprintRepository.getReferenceById(sprintId)
        val epicToLinkWithSprint = epicRepository.getReferenceById(epicId)

        sprintToLinkWithEpic.relatedEpic.sprints.remove(sprintToLinkWithEpic)
        sprintToLinkWithEpic.relatedEpic = epicToLinkWithSprint
        epicToLinkWithSprint.sprints.add(sprintToLinkWithEpic)

        return sprintRepository.save(sprintToLinkWithEpic)
    }

    fun linkSprintToIssue(sprintId: Long, issueId: Long): Sprint {
        val sprintToLinkWithIssue = sprintRepository.getReferenceById(sprintId)
        val issueToLinkWithSprint = issueRepository.getReferenceById(issueId)

        sprintToLinkWithIssue.issues.add(issueToLinkWithSprint)
        issueToLinkWithSprint.relatedSprint = sprintToLinkWithIssue

        return sprintRepository.save(sprintToLinkWithIssue)
    }

    fun unlinkSprintFromIssue(sprintId: Long, issueId: Long): Sprint {
        val sprintToUnlinkWithIssue = sprintRepository.getReferenceById(sprintId)
        val issueToUnlinkWithSprint = issueRepository.getReferenceById(issueId)

        if (sprintToUnlinkWithIssue.issues.remove(issueToUnlinkWithSprint))
            issueToUnlinkWithSprint.relatedSprint = null

        return sprintRepository.save(sprintToUnlinkWithIssue)
    }
}
