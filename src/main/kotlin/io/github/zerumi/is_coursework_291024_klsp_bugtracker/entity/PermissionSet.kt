package io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity

import jakarta.persistence.*
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.GrantedAuthority
import java.io.Serializable

/*
    CREATE TABLE PermissionSet (
        permission_set_id BIGSERIAL PRIMARY KEY,
        permission_set_name TEXT NOT NULL,
        permission_set BIGINT NOT NULL,
    );
 */

@Entity
@Table(name = "CoursePermissionSet", schema = "s367837")
class PermissionSet(
    @Id @GeneratedValue @Column(name = "permission_set_id") var id: Long? = null,
    @Column(name = "permission_set_name", unique = true) var name: String,
    @OneToMany(mappedBy = "permissionSet") var assignedUsers: MutableCollection<User> = mutableListOf(),
    // permissions
    @Column(name = "permission_set") var permissions: ULong,
) : GrantedAuthority, Serializable {
    override fun getAuthority(): String = name
}

/**
 * Returns `true` if `testPermission` contains in `permissions`, otherwise `false`
 *
 * @author Zerumi
 * @since 1.0
 */
fun checkPermission(permissions: ULong, testPermission: Permissions): Boolean =
    testPermission and permissions != 0.toULong()

fun requireOwnership(userId: Long, testObjUserId: Long, orElse: () -> Unit) =
    if (userId != testObjUserId) orElse() else Unit

fun requirePermissions(permissions: ULong, testPermissions: ULong) =
    if (testPermissions and permissions == 0.toULong()) throw AccessDeniedException("Permission denied") else Unit

enum class Permissions {
    PRIVILEGED,
    CREATE_ISSUE, UPDATE_ANY_ISSUE,
    CREATE_COMMENT, UPDATE_ANY_COMMENT,
    MANAGE_EPIC,
    MANAGE_SPRINT,
    MANAGE_EVENTS,
    MANAGE_TAGS,
    ;

    private val value: ULong = PermissionValueGenerator.nextValue

    infix fun or(other: ULong) = this.value or other
    infix fun and(other: ULong) = this.value and other
    infix fun or(other: Permissions) = this.value or other.value
    infix fun and(other: Permissions) = this.value and other.value

    /*
    Following code lead to NPE in runtime -- https://youtrack.jetbrains.com/issue/KT-37165

    companion object {
        private var nextValue: ULong = 1u
            get() {
                field *= 2u
                return field
            }
    }
    */
}

private class PermissionValueGenerator {
    companion object {
        var nextValue: ULong = 1u
            get() {
                field *= 2u
                return field
            }
            private set
    }
}
