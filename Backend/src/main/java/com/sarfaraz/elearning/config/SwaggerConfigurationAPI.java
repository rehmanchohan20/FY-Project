package com.sarfaraz.elearning.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfigurationAPI {

	@Bean
	public GroupedOpenApi userApi() {
		return GroupedOpenApi.builder().group("registration-api").pathsToMatch("/v1/auth/registeration").build();
	}

	@Bean
	public GroupedOpenApi registerApi() {
		return GroupedOpenApi.builder().group("login-api").pathsToMatch("/v1/auth/login").build();
	}

	@Bean
	public GroupedOpenApi AuthApi() {
		return GroupedOpenApi.builder().group("Auth-api").pathsToMatch("/Google/auth/Redirect").build();
	}

	// /Google/auth
}