package io.github.zerumi.is_coursework_291024_klsp_bugtracker.controller

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.annotation.RequirePermission
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.*
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Permissions
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.service.IssueService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.parameters.P
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/issue")
class IssueController(
    val issueService: IssueService
) {
    @GetMapping("/{id}")
    fun getIssue(@PathVariable id: Long): IssueDTO =
        toDTO(issueService.getById(id))

    @GetMapping("/page/{pageNo}/{issuesPerPage}")
    fun getIssues(@PathVariable pageNo: Int, @PathVariable issuesPerPage: Int): List<IssueDTO> =
        issueService.getIssues(pageNo, issuesPerPage).map { toDTO(it) }

    @PostMapping
    @RequirePermission(Permissions.CREATE_ISSUE)
    @SecurityRequirement(name = "Bearer Authentication")
    fun createIssue(@RequestBody issueModelDTO: IssueRequestDTO): IssueDTO =
        toDTO(issueService.createIssue(issueModelDTO))

    @PutMapping
    @PreAuthorize("hasPermission(#issue, 'IssueInfo', 'UPDATE_ANY_ISSUE')")
    @SecurityRequirement(name = "Bearer Authentication")
    fun updateIssue(@RequestBody @P("issue") issueInfo: IssueInfo): IssueDTO =
        toDTO(issueService.updateIssue(issueInfo))

    // todo delete by id with autocomplete issue info & preauthorize

    @DeleteMapping
    @PreAuthorize("hasPermission(#issue, 'IssueInfo', 'UPDATE_ANY_ISSUE')")
    @SecurityRequirement(name = "Bearer Authentication")
    fun deleteIssue(@RequestBody @P("issue") issueInfo: IssueInfo): Unit =
        issueService.deleteIssue(issueInfo)
}
