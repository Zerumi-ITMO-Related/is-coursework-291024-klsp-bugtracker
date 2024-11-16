package io.github.zerumi.is_coursework_291024_klsp_bugtracker.security

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.User

interface OwnedObject {
    fun getOwner() : User
}
