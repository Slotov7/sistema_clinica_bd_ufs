package sistema_clinica.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        // Bloco de informações gerais da API
        info = @Info(
                title = "API do SAICE",
                version = "v1.0",
                description = "API REST para disciplina de banco de dados, desenvolvida com Spring Boot."
        )
)

public class OpenApiConfig {
}
