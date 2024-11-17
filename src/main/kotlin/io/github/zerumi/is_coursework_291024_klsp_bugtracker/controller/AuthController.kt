package io.github.zerumi.is_coursework_291024_klsp_bugtracker.controller

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.AuthRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.JWTTokenResponseDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.RegisterRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.service.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth API", description = "Endpoints for authentication and get token access for most of the API")
class AuthController(val authenticationService: AuthService) {
    @PostMapping("/register")
    @Operation(
        summary = "Registers client in system.",
        description = "Create new user account in database."
    )
    fun register(@RequestBody request: RegisterRequestDTO): JWTTokenResponseDTO =
        authenticationService.register(request)

    @PostMapping("/login")
    @Operation(
        summary = "Generates a new JWT for user requests",
        description = "Generate a new JWT for user requests and using username and password"
    )
    fun login(@RequestBody request: AuthRequestDTO): JWTTokenResponseDTO =
        authenticationService.login(request)
}
