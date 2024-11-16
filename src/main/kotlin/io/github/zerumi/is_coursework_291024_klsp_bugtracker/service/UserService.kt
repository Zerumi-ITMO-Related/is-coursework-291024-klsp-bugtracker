package io.github.zerumi.is_coursework_291024_klsp_bugtracker.service

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.User
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository): UserDetailsService {
    fun getCurrentUser(): User {
        val login = SecurityContextHolder.getContext().authentication.name
        return getByLogin(login)
    }

    fun getByLogin(login: String): User {
        return userRepository.findByLogin(login) ?: throw UsernameNotFoundException("User not found")
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        requireNotNull(username)

        return getByLogin(username)
    }

    fun create(user: User): User {
        require(!userRepository.existsByLogin(user.login)) { "User ${user.login} already exists" }

        return userRepository.save(user)
    }
}
