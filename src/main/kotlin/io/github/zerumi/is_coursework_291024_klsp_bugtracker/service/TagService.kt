package io.github.zerumi.is_coursework_291024_klsp_bugtracker.service

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.TagInfo
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.TagRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Epic
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Tag
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository.TagRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class TagService(
    val tagRepository: TagRepository
) {
    fun getTags(pageNumber: Int, issuesPerPage: Int, sortProperty: String = "id"): List<Tag> =
        tagRepository.findAll(PageRequest.of(pageNumber, issuesPerPage, Sort.by(sortProperty))).toList()

    fun getById(id: Long?) = tagRepository.getReferenceById(requireNotNull(id))

    fun createTag(tagRequestDTO: TagRequestDTO): Tag {
        val tag = Tag(
            name = tagRequestDTO.name,
            color = tagRequestDTO.color,
        )

        return tagRepository.save(tag)
    }

    fun updateTag(tagInfo: TagInfo): Tag {
        val tagToUpdate = tagRepository.getReferenceById(requireNotNull(tagInfo.id))

        tagToUpdate.name = tagInfo.name
        tagToUpdate.color = tagInfo.color

        return tagRepository.save(tagToUpdate)
    }

    fun deleteTag(tagInfo: TagInfo) = tagRepository.deleteById(requireNotNull(tagInfo.id))
}
