package io.github.zerumi.is_coursework_291024_klsp_bugtracker.controller

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.*
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

    @PostMapping("/newIssue")
    @SecurityRequirement(name = "Bearer Authentication")
    fun submitIssue(issueModelDTO: IssueRequestDTO) {
        return issueService.openIssue(issueModelDTO)
    }
}
