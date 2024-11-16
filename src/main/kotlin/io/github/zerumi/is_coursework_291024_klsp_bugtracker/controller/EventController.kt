package io.github.zerumi.is_coursework_291024_klsp_bugtracker.controller

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.annotation.RequirePermission
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.*
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Permissions
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.service.EventService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.parameters.P
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/event")
class EventController(
    val eventService: EventService,
) {
    @GetMapping("/{id}")
    fun getEvent(@PathVariable id: Long): EventDTO {
        return toDTO(eventService.getById(id))
    }

    @GetMapping("/page/{pageNo}/{eventsPerPage}")
    fun getEvents(@PathVariable pageNo: Int, @PathVariable eventsPerPage: Int): List<EventDTO> {
        val issues = eventService.getEvents(pageNo, eventsPerPage)
        return issues.map { toDTO(it) }
    }

    @PostMapping
    @RequirePermission(Permissions.MANAGE_EVENTS)
    @SecurityRequirement(name = "Bearer Authentication")
    fun createEvent(eventRequestDTO: EventRequestDTO) {
        return eventService.createEvent(eventRequestDTO)
    }

    @PutMapping
    @RequirePermission(Permissions.MANAGE_EVENTS)
    @SecurityRequirement(name = "Bearer Authentication")
    fun updateEvent(eventInfo: EventInfo) {
        return eventService.updateEvent(eventInfo)
    }

    @DeleteMapping
    @RequirePermission(Permissions.MANAGE_EVENTS)
    @SecurityRequirement(name = "Bearer Authentication")
    fun deleteEvent(@RequestBody @P("event") eventInfo: EventInfo) {
        return eventService.deleteEvent(eventInfo)
    }
}
