package io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity

import jakarta.persistence.*
import java.io.Serializable

/*
    CREATE TABLE User (
        user_id BIGSERIAL PRIMARY KEY,
        display_name VARCHAR(90) NOT NULL,
        login VARCHAR(90) NOT NULL,
        password TEXT NOT NULL,
        avatar_file_id BIGINT NOT NULL,
        permission_set_id BIGINT NOT NULL,
        FOREIGN KEY (avatar_file_id) REFERENCES File(file_id)
        FOREIGN KEY (permission_set_id) REFERENCES PermissionSet(permission_set_id)
    );
 */

@Entity
@Table(name = "CourseUser", schema = "s367837")
class User(
    @Id @GeneratedValue @Column(name = "user_id") var id: Long? = null,
    @Column(name = "display_name") var displayName: String,
    @Column(name = "login") var login: String,
    @Column(name = "password") var password: String,
    @OneToOne @JoinColumn(name = "avatar_file_id") var avatar: File,
    @ManyToOne @JoinColumn(name = "permission_set_id") var permissions: PermissionSet,

    @OneToMany(mappedBy = "user")
    var comments: MutableCollection<Comment>,
    @OneToMany(mappedBy = "user")
    var ratings: MutableCollection<Rating>,
) : Serializable
