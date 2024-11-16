package io.github.zerumi.is_coursework_291024_klsp_bugtracker.service

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.TagInfo
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.TagRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Tag
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository.TagRepository
import org.springframework.stereotype.Service

@Service
class TagService(
    val tagRepository: TagRepository
) {
    fun getById(id: Long?) = tagRepository.getReferenceById(requireNotNull(id))

    fun createTag(tagRequestDTO: TagRequestDTO) {
        val tag = Tag(
            name = tagRequestDTO.name,
            color = tagRequestDTO.color,
        )

        tagRepository.save(tag)
    }

    fun updateTag(tagInfo: TagInfo) {
        val tagToUpdate = tagRepository.getReferenceById(requireNotNull(tagInfo.id))

        tagToUpdate.name = tagInfo.name
        tagToUpdate.color = tagInfo.color

        tagRepository.save(tagToUpdate)
    }

    fun deleteTag(tagInfo: TagInfo) {
        tagRepository.deleteById(requireNotNull(tagInfo.id))
    }
}
