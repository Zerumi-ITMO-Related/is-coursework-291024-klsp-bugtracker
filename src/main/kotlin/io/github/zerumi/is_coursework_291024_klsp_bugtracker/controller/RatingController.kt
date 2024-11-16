package io.github.zerumi.is_coursework_291024_klsp_bugtracker.controller

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.RatingDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.RatingRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.toDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.service.RatingService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/rating")
class RatingController(
    val ratingService: RatingService,
) {
    @PostMapping
    @SecurityRequirement(name = "Bearer Authentication")
    fun createRating(@RequestBody ratingRequest: RatingRequestDTO): RatingDTO =
        toDTO(ratingService.createRating(ratingRequest))

    @DeleteMapping
    @SecurityRequirement(name = "Bearer Authentication")
    fun deleteRating(@RequestBody ratingRequest: RatingRequestDTO): Unit =
        ratingService.removeRating(ratingRequest)
}
