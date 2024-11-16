package io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Rating
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.RatingValue
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RatingRepository : JpaRepository<Rating, Long> {
    fun findByRatingAndComment_IdAndUser(rating: RatingValue, commentId: Long, user: User) : Rating?
}
