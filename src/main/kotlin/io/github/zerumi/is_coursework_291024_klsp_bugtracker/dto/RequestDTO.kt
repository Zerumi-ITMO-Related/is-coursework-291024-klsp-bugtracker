package io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto

data class IssueRequestDTO(
    val title: String,
    val content: String,
    val attachedFilesIds: List<Long>
)
