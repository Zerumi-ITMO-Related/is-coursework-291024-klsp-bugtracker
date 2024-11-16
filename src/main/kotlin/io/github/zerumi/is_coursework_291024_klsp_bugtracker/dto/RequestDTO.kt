package io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto

import java.io.Serializable

data class AuthRequestDTO(val login: String, val password: String) : Serializable

data class IssueRequestDTO(
    val title: String, val content: String, val attachedFilesIds: List<Long>
) : Serializable
