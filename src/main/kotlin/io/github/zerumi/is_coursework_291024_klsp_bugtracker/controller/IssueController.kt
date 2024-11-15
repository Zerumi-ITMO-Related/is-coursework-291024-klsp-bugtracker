package io.github.zerumi.is_coursework_291024_klsp_bugtracker.controller

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.annotation.RequirePermission
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.*
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Permissions
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.service.IssueService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/issue")
class IssueController(val issueService: IssueService) {
    @GetMapping("/page/{pageNo}/{issuesPerPage}")
    fun getIssues(@PathVariable pageNo: Int, @PathVariable issuesPerPage: Int): List<IssueDTO> {
        val issues = issueService.getIssues(pageNo, issuesPerPage)
        return issues.map { toDTO(it) }
    }

    @PostMapping
    @RequirePermission(Permissions.CREATE_ISSUE)
    @SecurityRequirement(name = "Bearer Authentication")
    fun submitIssue(@RequestBody issueModelDTO: IssueRequestDTO) {
        return issueService.openIssue(issueModelDTO)
    }

    @PutMapping
    @SecurityRequirement(name = "Bearer Authentication")
    fun updateIssue(@RequestBody issueDTO: IssueDTO) {
        return issueService.updateIssue(issueDTO)
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    fun deleteIssueById(id: Long) {
        return issueService.deleteIssue(id)
    }
}
