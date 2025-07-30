package com.example.siemens_initial_project.siemens_initial_project.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Siemens Initial Project API",
                description = "This is the API documentation for the Siemens Initial Project.\nIt allows managing tasks with features like create, update, delete, filter, and complete tasks.",
                version = "1.0.0",
                termsOfService = "https://example.com/terms",
                contact = @Contact(
                        name = "Abdul Shafi",
                        email = "mohamedsadik763@gmail.com",
                        url = "https://www.linkedin.com/in/abdulshafi/"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        servers = {
            @Server(
                    url = "http://localhost:8080",
                    description = "Local Development Server"
            )
        }
)
public class OpenApiConfiguration {
}
