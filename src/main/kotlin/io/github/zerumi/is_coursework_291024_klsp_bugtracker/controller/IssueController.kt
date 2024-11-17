package io.github.zerumi.is_coursework_291024_klsp_bugtracker.controller

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.annotation.RequirePermission
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.IssueDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.IssueInfo
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.IssueRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.toDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Permissions
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.service.IssueService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.parameters.P
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/issue")
@Tag(name = "Issue API", description = "Endpoints for managing issues")
class IssueController(
    val issueService: IssueService
) {
    @GetMapping("/{id}")
    @Operation(summary = "Get an issue by its id")
    fun getIssue(@PathVariable id: Long): IssueDTO =
        toDTO(issueService.getById(id))

    @GetMapping("/page/{pageNo}/{issuesPerPage}")
    @Operation(
        summary = "Get issues in paged view",
        description = "All issues on page sorted by it's ratio value"
    )
    fun getIssues(@PathVariable pageNo: Int, @PathVariable issuesPerPage: Int): List<IssueDTO> =
        issueService.getIssues(pageNo, issuesPerPage).map { toDTO(it) }

    @PostMapping
    @RequirePermission(Permissions.CREATE_ISSUE)
    @Operation(
        summary = "Create an issue",
        description = "This operation requires CREATE_ISSUE permission"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    fun createIssue(@RequestBody issueModelDTO: IssueRequestDTO): IssueDTO =
        toDTO(issueService.createIssue(issueModelDTO))

    @PutMapping
    @PreAuthorize("hasPermission(#issue, 'IssueInfo', 'UPDATE_ANY_ISSUE')")
    @Operation(
        summary = "Update an issue",
        description = "This operation requires issue ownership or UPDATE_ANY_ISSUE permission"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    fun updateIssue(@RequestBody @P("issue") issueInfo: IssueInfo): IssueDTO =
        toDTO(issueService.updateIssue(issueInfo))

    // todo delete by id with autocomplete issue info & preauthorize

    @DeleteMapping
    @PreAuthorize("hasPermission(#issue, 'IssueInfo', 'UPDATE_ANY_ISSUE')")
    @Operation(
        summary = "Delete an issue",
        description = "This operation requires issue ownership or UPDATE_ANY_ISSUE permission"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    fun deleteIssue(@RequestBody @P("issue") issueInfo: IssueInfo): Unit =
        issueService.deleteIssue(issueInfo)

    @PutMapping("{issueId}/toSprint/{sprintId}")
    @RequirePermission(Permissions.MANAGE_SPRINT)
    @Operation(
        summary = "Link issue to scrum sprint",
        description = "This operation requires MANAGE_SPRINT permission"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    fun linkToSprint(@PathVariable issueId: Long, @PathVariable sprintId: Long): IssueDTO =
        toDTO(issueService.linkIssueToSprint(issueId, sprintId))

    @PutMapping("{subIssueId}/toIssue/{issueId}")
    @RequirePermission(Permissions.MANAGE_ISSUE)
    @Operation(
        summary = "Link subIssue to issue",
        description = "This operation requires MANAGE_ISSUE permission"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    fun linkToIssue(@PathVariable subIssueId: Long, @PathVariable issueId: Long): IssueDTO =
        toDTO(issueService.linkSubIssueToIssue(subIssueId, issueId))

    @PutMapping("{issueId}/toTag/{tagId}")
    @RequirePermission(Permissions.MANAGE_TAG)
    @Operation(
        summary = "Link issue to tag",
        description = "This operation requires MANAGE_TAG permission"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    fun linkIssueToTag(@PathVariable issueId: Long, @PathVariable tagId: Long): IssueDTO =
        toDTO(issueService.linkIssueToTag(issueId, tagId))

    @PutMapping("{issueId}/toEvent/{eventId}")
    @RequirePermission(Permissions.MANAGE_EVENT)
    @Operation(
        summary = "Link issue to scrum event",
        description = "This operation requires MANAGE_EVENT permission"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    fun linkIssueToEvent(@PathVariable issueId: Long, @PathVariable eventId: Long): IssueDTO =
        toDTO(issueService.linkIssueToEvent(issueId, eventId))

    @DeleteMapping("fromSprint/{issueId}")
    @RequirePermission(Permissions.MANAGE_SPRINT)
    @Operation(
        summary = "Unlink issue from scrum sprint",
        description = "This operation requires MANAGE_SPRINT permission"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    fun unlinkFromSprint(@PathVariable issueId: Long): IssueDTO =
        toDTO(issueService.unlinkIssueFromSprint(issueId))

    @DeleteMapping("fromIssue/{issueId}")
    @RequirePermission(Permissions.MANAGE_ISSUE)
    @Operation(
        summary = "Unlink subIssue from issue",
        description = "This operation requires MANAGE_ISSUE permission"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    fun unlinkFromIssue(@PathVariable issueId: Long): IssueDTO =
        toDTO(issueService.unlinkSubIssueFromIssue(issueId))

    @DeleteMapping("fromTag/{issueId}/{tagId}")
    @RequirePermission(Permissions.MANAGE_TAG)
    @Operation(
        summary = "Unlink issue from tag",
        description = "This operation requires MANAGE_TAG permission"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    fun unlinkFromTag(@PathVariable issueId: Long, @PathVariable tagId: Long): IssueDTO =
        toDTO(issueService.unlinkIssueFromTag(issueId, tagId))

    @DeleteMapping("fromEvent/{issueId}/{eventId}")
    @RequirePermission(Permissions.MANAGE_EVENT)
    @Operation(
        summary = "Unlink issue from scrum event",
        description = "This operation requires MANAGE_EVENT permission"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    fun unlinkFromEvent(@PathVariable issueId: Long, @PathVariable eventId: Long): IssueDTO =
        toDTO(issueService.unlinkIssueFromEvent(issueId, eventId))
}
