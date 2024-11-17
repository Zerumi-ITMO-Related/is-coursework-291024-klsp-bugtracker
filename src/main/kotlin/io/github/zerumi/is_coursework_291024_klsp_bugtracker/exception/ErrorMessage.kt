package io.github.zerumi.is_coursework_291024_klsp_bugtracker.exception

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable
import java.util.*

@Schema(description = "Error message model")
data class ErrorMessage(
    @Schema(example = "404") val statusCode: Int,
    @Schema(example = "2024-04-11T12:00:00.000Z") val timestamp: Date,
    @Schema(example = "Resource not found") val description: String,
    @Schema(example = "The requested resource could not be found") val message: String?,
) : Serializable
