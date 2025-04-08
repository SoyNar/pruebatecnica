package com.springboot.pruebatecnica.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;


@OpenAPIDefinition(
        info = @Info(
                title = "API de Producros",
                description = "Endpoints para api de productos prueba tecnica backend",
                version = "1.0.1"
        )
)
public class SwaggerConfig {
}
