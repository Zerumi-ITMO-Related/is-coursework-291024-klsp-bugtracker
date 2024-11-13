package io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity

import jakarta.persistence.*
import java.io.Serializable
import java.time.ZonedDateTime

/*
    CREATE TABLE Epic (
        epic_id BIGSERIAL PRIMARY KEY,
        epic_name TEXT NOT NULL,
        epic_description TEXT NOT NULL,
        epic_deadline TIMESTAMP NOT NULL
    );
 */

@Entity
@Table(name = "CourseEpic", schema = "s367837")
class Epic(
    @Id @GeneratedValue @Column(name = "epic_id") var id: Long? = null,
    @Column(name = "epic_name") var name: String,
    @Column(name = "epic_description") var description: String,
    @Column(name = "epic_deadline") var deadline: ZonedDateTime,

    @OneToMany(mappedBy = "relatedEpic") var sprints: Collection<Sprint>
) : Serializable
