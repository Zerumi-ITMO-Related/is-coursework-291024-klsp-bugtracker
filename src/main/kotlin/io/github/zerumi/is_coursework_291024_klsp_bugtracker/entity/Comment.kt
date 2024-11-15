package io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity

import jakarta.persistence.*
import java.io.Serializable
import java.time.ZonedDateTime

/*
    CREATE TABLE Comment (
        comment_id BIGSERIAL PRIMARY KEY,
        user_id BIGINT NOT NULL,
        created_at TIMESTAMP NOT NULL,
        last_modified_at TIMESTAMP NOT NULL,
        parent_comment BIGINT NULL,
        content TEXT NOT NULL,
        FOREIGN KEY (user_id) REFERENCES User(user_id),
        FOREIGN KEY (parent_comment) REFERENCES Comment(comment_id),
        CONSTRAINT check_parent_comment_null CHECK (
            parent_comment IS NULL OR EXISTS (
                SELECT 1 FROM Issue WHERE comment_id = parent_comment
            )
        )
    );
 */

@Entity
@Table(name = "CourseComment", schema = "s367837")
class Comment(
    @Id @GeneratedValue @Column(name = "comment_id") var id: Long? = null,
    @ManyToOne @JoinColumn(name = "user_id") var user: User,
    @Column(name = "created_at") var creationTime: ZonedDateTime,
    @Column(name = "last_modified_at") var lastModified: ZonedDateTime?,
    @ManyToOne @JoinColumn(name = "parent_comment", nullable = true) var parentComment: Comment?,
    @Column(name = "content") var content: String,

    @OneToMany(mappedBy = "parentComment") var subThread: MutableCollection<Comment> = mutableListOf(),
    @OneToMany(mappedBy = "relatedComment") var attachedFiles: MutableCollection<File> = mutableListOf(),
    @OneToMany(mappedBy = "comment") var ratings: MutableCollection<Rating> = mutableListOf(),
) : Serializable
