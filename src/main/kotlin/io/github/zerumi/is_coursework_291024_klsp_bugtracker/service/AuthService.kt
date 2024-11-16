package io.github.zerumi.is_coursework_291024_klsp_bugtracker.service

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.AuthRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.JWTTokenResponseDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.RegisterRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.User
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.init.DefaultPermissionSet
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class AuthService(
    val userService: UserService,
    val jwtService: JWTService,
    val passwordEncoder: PasswordEncoder,
    val authManager: AuthenticationManager
) {
    fun register(request: RegisterRequestDTO): JWTTokenResponseDTO {
        val user = User(
            displayName = request.displayName,
            login = request.username,
            pass = passwordEncoder.encode(request.password),
            permissionSet = DefaultPermissionSet.DEFAULT_PERMISSION_SET,
        )

        userService.create(user)

        val jwt = jwtService.generateToken(user)
        return JWTTokenResponseDTO(jwt)
    }

    fun login(request: AuthRequestDTO): JWTTokenResponseDTO {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.login,
                request.password
            )
        )

        val user = userService.getByLogin(request.login)

        val jwt = jwtService.generateToken(user)
        return JWTTokenResponseDTO(jwt)
    }
}
