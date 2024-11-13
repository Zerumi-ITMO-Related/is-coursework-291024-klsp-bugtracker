package io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity

import jakarta.persistence.*
import java.io.Serializable

/*
    CREATE TABLE PermissionSet (
        permission_set_id BIGSERIAL PRIMARY KEY,
        permission_set_name TEXT NOT NULL,
        can_create_issue BOOLEAN NOT NULL,
        can_edit_issue BOOLEAN NOT NULL,
        can_delete_issue BOOLEAN NOT NULL,
        can_change_role BOOLEAN NOT NULL,
        can_create_sprint BOOLEAN NOT NULL,
        can_edit_sprint BOOLEAN NOT NULL,
        can_delete_sprint BOOLEAN NOT NULL,
        can_create_epic BOOLEAN NOT NULL,
        can_edit_epic BOOLEAN NOT NULL,
        can_delete_epic BOOLEAN NOT NULL,
        can_create_event BOOLEAN NOT NULL,
        can_edit_event BOOLEAN NOT NULL,
        can_delete_event BOOLEAN NOT NULL
    );
 */

@Entity
@Table(name = "CoursePermissionSet", schema = "s367837")
class PermissionSet(
    @Id @GeneratedValue @Column(name = "permission_set_id") var id: Long? = null,
    @Column(name = "permission_set_name") var name: String,
    @OneToMany(mappedBy = "permissions") var assignedUsers: Collection<User>
    // permissions
) : Serializable
