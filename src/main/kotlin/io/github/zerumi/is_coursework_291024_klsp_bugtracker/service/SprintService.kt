package io.github.zerumi.is_coursework_291024_klsp_bugtracker.service

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.SprintInfo
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.SprintRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Sprint
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository.SprintRepository
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class SprintService(
    val epicService: EpicService,
    val sprintRepository: SprintRepository
) {
    fun getById(id: Long?) = sprintRepository.getReferenceById(requireNotNull(id))

    fun createSprint(sprintRequestDTO: SprintRequestDTO) {
        val sprint = Sprint(
            name = sprintRequestDTO.name,
            description = sprintRequestDTO.description,
            createdAt = ZonedDateTime.now(),
            deadline = sprintRequestDTO.deadline,
            relatedEpic = epicService.getById(sprintRequestDTO.relatedEpicId),
        )

        sprintRepository.save(sprint)
    }

    fun updateSprint(sprintInfo: SprintInfo) {
        val sprintToUpdate = sprintRepository.getReferenceById(requireNotNull(sprintInfo.id))

        sprintToUpdate.name = sprintInfo.name
        sprintToUpdate.description = sprintInfo.description
        sprintToUpdate.deadline = sprintInfo.deadline

        sprintRepository.save(sprintToUpdate)
    }

    fun deleteSprint(sprintInfo: SprintInfo) {
        sprintRepository.deleteById(requireNotNull(sprintInfo.id))
    }
}
