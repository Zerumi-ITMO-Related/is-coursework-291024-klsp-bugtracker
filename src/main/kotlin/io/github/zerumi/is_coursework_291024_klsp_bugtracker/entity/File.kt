package io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity

import jakarta.persistence.*
import java.io.Serializable
import java.time.ZonedDateTime

/*
    CREATE TABLE File (
        file_id BIGSERIAL PRIMARY KEY,
        file_path TEXT NOT NULL,
        file_mime_type TEXT NOT NULL,
        file_upload_time TIMESTAMP NOT NULL,
        related_comment_id BIGINT NULL
    );
 */

@Entity
@Table(name = "CourseFile", schema = "s367837")
class File(
    @Id @GeneratedValue @Column(name = "file_id") var id: Long? = null,
    @Column(name = "file_path", columnDefinition = "text") var filepath: String,
    @Column(name = "file_mime_type", columnDefinition = "text") var mimeType: String,
    @Column(name = "file_upload_time") var uploadTime: ZonedDateTime,
    @ManyToOne @JoinColumn(name = "related_comment_id", nullable = true) var relatedComment: Comment? = null
) : Serializable
