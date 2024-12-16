package io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity

import jakarta.persistence.*
import java.io.Serializable
import java.time.ZonedDateTime

/*
    CREATE TABLE Event (
        event_id BIGSERIAL PRIMARY KEY,
        event_name TEXT NOT NULL,
        event_description TEXT NOT NULL,
        event_date TIMESTAMP NOT NULL,
        event_review TEXT NULL
    );

    CREATE TABLE EventIssue (
        surrogate_id BIGSERIAL PRIMARY KEY,
        event_id BIGINT NOT NULL,
        issue_id BIGINT NOT NULL,
        FOREIGN KEY (event_id) REFERENCES Event(event_id),
        FOREIGN KEY (issue_id) REFERENCES Issue(issue_id)
    );
 */

@Entity
@Table(name = "CourseEvent", schema = "s367837")
class Event(
    @Id @GeneratedValue @Column(name = "event_id") var id: Long? = null,
    @Column(name = "event_name", columnDefinition = "text") var name: String,
    @Column(name = "event_description", columnDefinition = "text") var description: String,
    @Column(name = "event_date") var date: ZonedDateTime,
    @Column(name = "event_review", nullable = true) var eventReview: String?,
    @ManyToMany @JoinTable(
        name = "CourseEventIssue",
        joinColumns = [JoinColumn(name = "event_id")],
        inverseJoinColumns = [JoinColumn(name = "issue_id")],
    ) var issues: MutableCollection<Issue> = mutableListOf(),
) : Serializable
