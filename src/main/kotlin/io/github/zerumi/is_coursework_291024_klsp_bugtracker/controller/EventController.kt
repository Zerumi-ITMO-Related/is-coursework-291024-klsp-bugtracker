package io.github.zerumi.is_coursework_291024_klsp_bugtracker.controller

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.annotation.RequirePermission
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.EventDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.EventInfo
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.EventRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.toDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Permissions
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.service.EventService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/event")
class EventController(
    val eventService: EventService,
) {
    @GetMapping("/{id}")
    fun getEvent(@PathVariable id: Long): EventDTO =
        toDTO(eventService.getById(id))

    @GetMapping("/page/{pageNo}/{eventsPerPage}")
    fun getEvents(@PathVariable pageNo: Int, @PathVariable eventsPerPage: Int): List<EventDTO> =
        eventService.getEvents(pageNo, eventsPerPage).map { toDTO(it) }

    @PostMapping
    @RequirePermission(Permissions.MANAGE_EVENTS)
    @SecurityRequirement(name = "Bearer Authentication")
    fun createEvent(@RequestBody eventRequestDTO: EventRequestDTO): EventDTO =
        toDTO(eventService.createEvent(eventRequestDTO))

    @PutMapping
    @RequirePermission(Permissions.MANAGE_EVENTS)
    @SecurityRequirement(name = "Bearer Authentication")
    fun updateEvent(@RequestBody eventInfo: EventInfo): EventDTO =
        toDTO(eventService.updateEvent(eventInfo))

    @DeleteMapping
    @RequirePermission(Permissions.MANAGE_EVENTS)
    @SecurityRequirement(name = "Bearer Authentication")
    fun deleteEvent(@RequestBody eventInfo: EventInfo): Unit =
        eventService.deleteEvent(eventInfo)

    @PutMapping("{eventId}/toIssue/{issueId}")
    @RequirePermission(Permissions.MANAGE_EVENTS)
    @SecurityRequirement(name = "Bearer Authentication")
    fun linkEventWithIssue(@PathVariable eventId: Long, @PathVariable issueId: Long): EventDTO =
        toDTO(eventService.linkEventWithIssue(eventId, issueId))

    @DeleteMapping("{eventId}/fromIssue/{issueId}")
    @RequirePermission(Permissions.MANAGE_EVENTS)
    @SecurityRequirement(name = "Bearer Authentication")
    fun unlinkEventWithIssue(@PathVariable eventId: Long, @PathVariable issueId: Long): EventDTO =
        toDTO(eventService.unlinkEventWithIssue(eventId, issueId))
}
