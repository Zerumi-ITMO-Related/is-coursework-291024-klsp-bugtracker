package io.github.zerumi.is_coursework_291024_klsp_bugtracker.service

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.EventInfo
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.EventRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Event
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository.EventRepository
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository.IssueRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class EventService(
    val eventRepository: EventRepository,
    val issueRepository: IssueRepository,
) {
    fun getById(id: Long?) = eventRepository.getReferenceById(requireNotNull(id))

    fun getEvents(pageNumber: Int, issuesPerPage: Int, sortProperty: String = "name"): List<Event> =
        eventRepository.findAll(PageRequest.of(pageNumber, issuesPerPage, Sort.by(sortProperty))).toList()

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun createEvent(eventRequestDTO: EventRequestDTO): Event {
        val event = Event(
            name = eventRequestDTO.name,
            description = eventRequestDTO.description,
            date = eventRequestDTO.date,
            eventReview = null,
        )

        return eventRepository.save(event)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun updateEvent(eventInfo: EventInfo): Event {
        val eventToUpdate = eventRepository.getReferenceById(requireNotNull(eventInfo.id))

        eventToUpdate.name = eventInfo.name
        eventToUpdate.description = eventInfo.description
        eventToUpdate.date = eventInfo.date
        eventToUpdate.eventReview = eventInfo.eventReview

        return eventRepository.save(eventToUpdate)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun deleteEvent(eventInfo: EventInfo) = eventRepository.deleteById(requireNotNull(eventInfo.id))

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun linkEventToIssue(eventId: Long, issueId: Long): Event {
        val eventToLinkWithIssue = eventRepository.getReferenceById(eventId)
        val issueToLinkWithEvent = issueRepository.getReferenceById(issueId)

        eventToLinkWithIssue.issues.add(issueToLinkWithEvent)
        issueToLinkWithEvent.events.add(eventToLinkWithIssue)

        return eventRepository.save(eventToLinkWithIssue)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun unlinkEventFromIssue(eventId: Long, issueId: Long): Event {
        val eventToUnlinkWithIssue = eventRepository.getReferenceById(eventId)
        val issueToUnlinkWithEvent = issueRepository.getReferenceById(issueId)

        eventToUnlinkWithIssue.issues.remove(issueToUnlinkWithEvent)
        issueToUnlinkWithEvent.events.remove(eventToUnlinkWithIssue)

        return eventRepository.save(eventToUnlinkWithIssue)
    }
}
