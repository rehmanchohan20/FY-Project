package com.sarfaraz.elearning.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfigurationAPI {

	@Bean
	public GroupedOpenApi registrationApi() {
		return GroupedOpenApi.builder()
				.group("registration-api").pathsToMatch("/v1/auth/registeration").build();
	}

	@Bean
	public GroupedOpenApi loginApi() {
		return GroupedOpenApi.builder().group("login-api").pathsToMatch("/v1/auth/login").build();
	}

	@Bean
	public GroupedOpenApi googleAuthApi() {
		return GroupedOpenApi.builder().group("google-auth-api").pathsToMatch("/Google/auth/Redirect").build();
	}

	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder().group("public-api").pathsToMatch("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html","/v1/auth/**", "/auth/**", "/oauth2/**", "/send-email","/api/public/**", "/api/payments/**", "/api/course-progress/**","/api/users/**", "/api/media/upload", "/user/me").build();
	}

	@Bean
	public GroupedOpenApi courseApi() {
		return GroupedOpenApi.builder().group("course-api").pathsToMatch("/api/courses/**", "/api/modules/{moduleId}/lessons/**","/api/modules/{moduleId}/lessons", "/api/courses/{courseId}/offers/**").build();
	}

	@Bean
	public GroupedOpenApi adminApi() {
		return GroupedOpenApi.builder().group("admin-api").pathsToMatch("/api/users/admin/**").build();
	}

	@Bean
	public GroupedOpenApi oauthApi() {
		return GroupedOpenApi.builder().group("oauth-api").pathsToMatch("/oauth2/**", "/oauth2/callback/*").build();
	}
}
