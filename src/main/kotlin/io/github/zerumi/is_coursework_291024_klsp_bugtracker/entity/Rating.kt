package io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity

import jakarta.persistence.*
import java.io.Serializable

/*
    CREATE TABLE Rating (
        rating_id BIGSERIAL PRIMARY KEY,
        comment_id BIGINT NOT NULL,
        user_id BIGINT NOT NULL,
        rating_value VARCHAR(10) NOT NULL CHECK (rating_value IN ('LIKE', 'DISLIKE')),
        FOREIGN KEY (comment_id) REFERENCES Comment(comment_id),
        FOREIGN KEY (user_id) REFERENCES User(user_id),
    );
 */

@Entity
@Table(name = "CourseRating", schema = "s367837", uniqueConstraints = [
    UniqueConstraint(columnNames = ["comment_id", "user_id"])
])
class Rating(
    @Id @GeneratedValue @Column(name = "rating_id") var id: Long? = null,
    @ManyToOne @JoinColumn(name = "comment_id") var comment: Comment,
    @ManyToOne @JoinColumn(name = "user_id") var user: User,
    @Enumerated(value = EnumType.STRING) @Column(name = "rating_value") var rating: RatingValue,
) : Serializable

enum class RatingValue : Serializable {
    LIKE, DISLIKE,
}
