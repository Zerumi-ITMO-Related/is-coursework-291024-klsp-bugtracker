package io.github.zerumi.is_coursework_291024_klsp_bugtracker.service

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.RatingRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Rating
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository.CommentRepository
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository.RatingRepository
import org.springframework.stereotype.Service

@Service
class RatingService(
    val commentRepository: CommentRepository,
    val ratingRepository: RatingRepository,
    val userService: UserService,
) {
    fun createRating(ratingRequestDTO: RatingRequestDTO): Rating {
        val rating = Rating(
            comment = commentRepository.getReferenceById(ratingRequestDTO.commentId),
            user = userService.getCurrentUser(),
            rating = ratingRequestDTO.rating
        )

        return ratingRepository.save(rating)
    }

    fun removeRating(ratingRequestDTO: RatingRequestDTO) {
        val rating = ratingRepository.findByRatingAndComment_IdAndUser(
            rating = ratingRequestDTO.rating,
            commentId = ratingRequestDTO.commentId,
            user = userService.getCurrentUser()
        )

        return ratingRepository.delete(requireNotNull(rating))
    }
}
