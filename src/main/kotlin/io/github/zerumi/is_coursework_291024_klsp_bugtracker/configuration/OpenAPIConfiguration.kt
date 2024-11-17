package io.github.zerumi.is_coursework_291024_klsp_bugtracker.configuration

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(
    info = Info(
        title = "Katána API",
        description = "Kotlin Language Server (Katána Project) bugtracker back-end", version = "1.0.0",
        contact = Contact(
            name = "Zerumi",
            email = "367837@edu.itmo.ru",
        )
    ),
    servers = [
        Server(url = "http://localhost:8080", description = "Default Server URL")
    ]
)
@SecurityScheme(
    name = "Bearer Authentication",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
class OpenAPISecurityConfiguration
