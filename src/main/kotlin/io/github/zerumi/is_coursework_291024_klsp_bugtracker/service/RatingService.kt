package io.github.zerumi.is_coursework_291024_klsp_bugtracker.service

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.RatingRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Rating
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.RatingValue
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository.CommentRepository
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository.RatingRepository
import org.springframework.stereotype.Service

@Service
class RatingService(
    val commentRepository: CommentRepository,
    val ratingRepository: RatingRepository,
    val userService: UserService,
    val issueService: IssueService,
) {
    fun createRating(ratingRequestDTO: RatingRequestDTO): Rating {
        val comment = commentRepository.getReferenceById(ratingRequestDTO.commentId)

        val rating = Rating(
            comment = comment, user = userService.getCurrentUser(), rating = ratingRequestDTO.rating
        )

        if (comment.parentComment == null) issueService.increaseRatio(
            requireNotNull(issueService.issueRepository.findByComment(comment).id),
            when (ratingRequestDTO.rating) {
                RatingValue.LIKE -> IssueService.INCREASE_PER_LIKE
                RatingValue.DISLIKE -> IssueService.INCREASE_PER_DISLIKE
            }
        )

        return ratingRepository.save(rating)
    }

    fun removeRating(ratingRequestDTO: RatingRequestDTO) {
        val rating = ratingRepository.findByRatingAndComment_IdAndUser(
            rating = ratingRequestDTO.rating,
            commentId = ratingRequestDTO.commentId,
            user = userService.getCurrentUser()
        )

        val comment = commentRepository.getReferenceById(ratingRequestDTO.commentId)

        if (comment.parentComment == null) issueService.increaseRatio(
            requireNotNull(issueService.issueRepository.findByComment(comment).id),
            -when (ratingRequestDTO.rating) {
                RatingValue.LIKE -> IssueService.INCREASE_PER_LIKE
                RatingValue.DISLIKE -> IssueService.INCREASE_PER_DISLIKE
            }
        )

        return ratingRepository.delete(requireNotNull(rating))
    }
}
