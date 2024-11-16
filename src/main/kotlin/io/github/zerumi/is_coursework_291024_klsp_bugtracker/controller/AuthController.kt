package io.github.zerumi.is_coursework_291024_klsp_bugtracker.controller

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.AuthRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.JWTTokenResponseDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.RegisterRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/auth")
class AuthController(val authenticationService: AuthService) {
    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequestDTO): JWTTokenResponseDTO {
        return authenticationService.register(request)
    }

    @PostMapping("/login")
    fun login(@RequestBody request: AuthRequestDTO): JWTTokenResponseDTO {
        return authenticationService.login(request)
    }
}
