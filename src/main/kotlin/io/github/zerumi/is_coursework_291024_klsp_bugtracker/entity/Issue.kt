package io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity

import jakarta.persistence.*
import java.io.Serializable

/*
    CREATE TABLE Issue (
        issue_id BIGSERIAL PRIMARY KEY,
        issue_title TEXT NOT NULL,
        first_comment_id BIGINT NOT NULL,
        related_sprint_id BIGINT NULL,
        parent_issue_id BIGINT NULL,
        FOREIGN KEY (comment_id) REFERENCES Comment(comment_id),
        FOREIGN KEY (sprint_id) REFERENCES Sprint(sprint_id),
        FOREIGN KEY (parent_task_id) REFERENCES Issue(issue_id)
    );

    CREATE TABLE EventIssue (
        surrogate_id BIGSERIAL PRIMARY KEY,
        event_id BIGINT NOT NULL,
        issue_id BIGINT NOT NULL,
        FOREIGN KEY (event_id) REFERENCES Event(event_id),
        FOREIGN KEY (issue_id) REFERENCES Issue(issue_id)
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
@Table(name = "CourseIssue", schema = "s367837")
class Issue(
    @Id @GeneratedValue @Column(name = "issue_id") var id: Long? = null,
    @Column(name = "issue_title") var title: String,
    @OneToOne @JoinColumn(name = "first_comment_id") var comment: Comment,
    @ManyToOne @JoinColumn(name = "related_sprint_id", nullable = true) var relatedSprint: Sprint?,
    @ManyToOne @JoinColumn(name = "parent_issue_id", nullable = true) var parentIssue: Issue?,
    @ManyToMany @JoinTable(
        name = "CourseIssueTag",
        joinColumns = [JoinColumn(name = "issue_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")],
    ) var tags: MutableCollection<Tag> = mutableListOf(),

    @ManyToMany(mappedBy = "issues") var events: MutableCollection<Event> = mutableListOf(),
    @OneToMany(mappedBy = "parentIssue") var subIssues: MutableCollection<Issue> = mutableListOf(),
) : Serializable
