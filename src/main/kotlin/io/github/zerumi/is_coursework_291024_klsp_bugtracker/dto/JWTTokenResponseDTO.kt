package io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

@Schema(description = "JWT token response Data object")
data class JWTTokenResponseDTO(
    @Schema(
        example = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MjUyLCJzdWIiOiJaZXJ1bWkiLCJpYXQiOjE3MzE4ODQ0NDQsImV4cCI6MTczMjAyODQ0NH0.a07DlL4EyYi1jklA_BO2dJ-xX3ONGiw6GBW6brzlwQk"
    ) val token: String
) : Serializable
