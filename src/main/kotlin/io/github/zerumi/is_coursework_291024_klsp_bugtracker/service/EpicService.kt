package io.github.zerumi.is_coursework_291024_klsp_bugtracker.service

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.EpicInfo
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.EpicRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Epic
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository.EpicRepository
import org.springframework.dao.CannotAcquireLockException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class EpicService(
    val epicRepository: EpicRepository,
) {
    fun getById(id: Long?) = epicRepository.getReferenceById(requireNotNull(id))

    fun getEpics(pageNumber: Int, issuesPerPage: Int, sortProperty: String = "name"): List<Epic> =
        epicRepository.findAll(PageRequest.of(pageNumber, issuesPerPage, Sort.by(sortProperty))).toList()

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = [CannotAcquireLockException::class])
    fun createEpic(epicRequestDTO: EpicRequestDTO): Epic {
        val epic = Epic(
            name = epicRequestDTO.name,
            description = epicRequestDTO.description,
            deadline = epicRequestDTO.deadline,
        )

        return epicRepository.save(epic)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = [CannotAcquireLockException::class])
    fun updateEpic(epicInfo: EpicInfo): Epic {
        val epicToUpdate = epicRepository.getReferenceById(requireNotNull(epicInfo.id))

        epicToUpdate.name = epicInfo.name
        epicToUpdate.description = epicInfo.description
        epicToUpdate.deadline = epicInfo.deadline

        return epicRepository.save(epicToUpdate)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = [CannotAcquireLockException::class])
    fun deleteEpic(epicInfo: EpicInfo) = epicRepository.deleteById(requireNotNull(epicInfo.id))
}
