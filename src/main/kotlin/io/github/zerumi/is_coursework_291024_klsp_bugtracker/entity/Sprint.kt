package io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity

import jakarta.persistence.*
import java.io.Serializable
import java.time.ZonedDateTime

/*
    CREATE TABLE Sprint (
        sprint_id BIGSERIAL PRIMARY KEY,
        sprint_name VARCHAR(90) NOT NULL,
        sprint_description TEXT NOT NULL,
        sprint_created_at TIMESTAMP NOT NULL,
        sprint_deadline TIMESTAMP NOT NULL,
        epic_id BIGINT NOT NULL,
        FOREIGN KEY (epic_id) REFERENCES Epic(epic_id)
    );
 */

@Entity
@Table(name = "CourseSprint", schema = "s367837")
class Sprint(
    @Id @GeneratedValue @Column(name = "sprint_id") var id: Long? = null,
    @Column(name = "sprint_name") var name: String,
    @Column(name = "sprint_description") var description: String,
    @Column(name = "sprint_created_at") var createdAt: ZonedDateTime,
    @Column(name = "sprint_deadline") var deadline: ZonedDateTime,
    @ManyToOne @JoinColumn(name = "epic_id") var relatedEpic: Epic,

    @OneToMany(mappedBy = "relatedSprint") var issues: MutableCollection<Issue> = mutableListOf()
) : Serializable
