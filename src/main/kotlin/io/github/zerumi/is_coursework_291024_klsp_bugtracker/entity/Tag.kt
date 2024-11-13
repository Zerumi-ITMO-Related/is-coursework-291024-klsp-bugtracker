package io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity

import jakarta.persistence.*
import java.io.Serializable

/*
    CREATE TABLE Tag (
        tag_id BIGSERIAL PRIMARY KEY,
        tag_name VARCHAR(90) NOT NULL,
        tag_color VARCHAR(90) NOT NULL
    );

    CREATE TABLE IssueTag (
        surrogate_id BIGSERIAL PRIMARY KEY,
        issue_id BIGINT NOT NULL,
        tag_id BIGINT NOT NULL,
        FOREIGN KEY (issue_id) REFERENCES Issue(issue_id),
        FOREIGN KEY (tag_id) REFERENCES Tag(tag_id)
    );
 */

@Entity
@Table(name = "CourseTag", schema = "s367837")
class Tag(
    @Id @GeneratedValue @Column(name = "tag_id") var id: Long? = null,
    @Column(name = "tag_name") var name: String,
    @Column(name = "tag_color") var color: String,
    @ManyToMany(mappedBy = "tags") var relatedIssues: Collection<Issue>
) : Serializable
