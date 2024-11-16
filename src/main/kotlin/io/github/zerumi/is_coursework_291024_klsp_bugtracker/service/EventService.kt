package io.github.zerumi.is_coursework_291024_klsp_bugtracker.service

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.EventInfo
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.EventRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Event
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository.EventRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class EventService(val eventRepository: EventRepository) {
    fun getById(id: Long?) = eventRepository.getReferenceById(requireNotNull(id))

    fun getEvents(pageNumber: Int, issuesPerPage: Int, sortProperty: String = "name"): List<Event> =
        eventRepository.findAll(PageRequest.of(pageNumber, issuesPerPage, Sort.by(sortProperty))).toList()

    fun createEvent(eventRequestDTO: EventRequestDTO) {
        val event = Event(
            name = eventRequestDTO.name,
            description = eventRequestDTO.description,
            date = eventRequestDTO.date,
            eventReview = null,
        )

        eventRepository.save(event)
    }

    fun updateEvent(eventInfo: EventInfo) {
        val eventToUpdate = eventRepository.getReferenceById(requireNotNull(eventInfo.id))

        eventToUpdate.name = eventInfo.name
        eventToUpdate.description = eventInfo.description
        eventToUpdate.date = eventInfo.date
        eventToUpdate.eventReview = eventInfo.eventReview

        eventRepository.save(eventToUpdate)
    }

    fun deleteEvent(eventInfo: EventInfo) {
        eventRepository.deleteById(requireNotNull(eventInfo.id))
    }
}
