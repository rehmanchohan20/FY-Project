package com.sarfaraz.elearning.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@OpenAPIDefinition(security = { @SecurityRequirement(name = "Authorization") })
@SecurityScheme(name = "Authorization", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
@Configuration
public class SwaggerConfiguration {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info().title("IGELP").version("1.0.0")
				.description("Final Year project of indus university")
				.contact(new io.swagger.v3.oas.models.info.Contact().name("Rehman"))
				.license(new io.swagger.v3.oas.models.info.License().name("Apache 2.0").url("http://springdoc.org")));
	}
}