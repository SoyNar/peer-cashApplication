package com.peercash.PeerCashproject.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(
        info = @Info(
                title = "API de Préstamos Peer-to-Peer",
                description = "API para la gestión de préstamos entre aplicantes e inversores en una plataforma P2P. La aplicación permite a los usuarios solicitar, aprobar y rechazar préstamos de manera rápida y sencilla. Los roles involucrados incluyen aplicantes, inversores y administradores, quienes gestionan y supervisan las solicitudes de préstamos. Esta plataforma está dirigida al mercado colombiano.",
                version = "1.0.0"
        ),
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT"
)

public class SwaggerConfig {

        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                        .components(new Components()
                                .addSchemas("RegisterRequestDto", new Schema<>()
                                        .type("object")
                                        .example("{\"document\":\"88696989\",\"email\":\"usuario@example.com\",\"name\":\"string\",\"lastname\":\"string\",\"birthday\":\"2025-03-22\",\"bankAccount\":\"441835911587\",\"userType\":\"string\",\"roles\":[\"string\"],\"city\":\"string\"}"))
                                .addSchemas("MultipartFile", new Schema<>()
                                        .type("string")
                                        .format("binary"))
                        );
        }
}
