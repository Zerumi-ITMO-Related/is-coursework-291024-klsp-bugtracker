package io.github.zerumi.is_coursework_291024_klsp_bugtracker.service

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.EpicInfo
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.EpicRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Epic
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository.EpicRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class EpicService(
    val epicRepository: EpicRepository,
) {
    fun getById(id: Long?) = epicRepository.getReferenceById(requireNotNull(id))

    fun getEpics(pageNumber: Int, issuesPerPage: Int, sortProperty: String = "name"): List<Epic> =
        epicRepository.findAll(PageRequest.of(pageNumber, issuesPerPage, Sort.by(sortProperty))).toList()

    fun createEpic(epicRequestDTO: EpicRequestDTO) {
        val epic = Epic(
            name = epicRequestDTO.name,
            description = epicRequestDTO.description,
            deadline = epicRequestDTO.deadline,
        )

        epicRepository.save(epic)
    }

    fun updateEpic(epicInfo: EpicInfo) {
        val epicToUpdate = epicRepository.getReferenceById(requireNotNull(epicInfo.id))

        epicToUpdate.name = epicInfo.name
        epicToUpdate.description = epicInfo.description
        epicToUpdate.deadline = epicInfo.deadline

        epicRepository.save(epicToUpdate)
    }

    fun deleteEpic(epicInfo: EpicInfo) {
        epicRepository.deleteById(requireNotNull(epicInfo.id))
    }
}