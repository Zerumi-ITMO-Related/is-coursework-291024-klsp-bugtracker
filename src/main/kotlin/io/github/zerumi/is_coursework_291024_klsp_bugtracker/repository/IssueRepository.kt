package io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Issue
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IssueRepository : JpaRepository<Issue, Long>
