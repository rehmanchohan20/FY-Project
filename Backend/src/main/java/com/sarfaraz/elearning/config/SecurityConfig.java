package com.sarfaraz.elearning.config;

import com.nimbusds.jose.jwk.*;
import com.nimbusds.jose.jwk.source.*;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.*;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import com.sarfaraz.elearning.service.auth.*;
import com.sarfaraz.elearning.service.impl.AuthServicImpl;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private RsaKeyConfigProperties rsaKeyConfigProperties;
	private AuthServicImpl authServicImpl;
	private OAuth2UserService oAuth2UserService;
	private CustomCorsConfiguration customCorsConfiguration;
	private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

	@Autowired
	public SecurityConfig(RsaKeyConfigProperties rsaKeyConfigProperties, AuthServicImpl authServicImpl,
						  OAuth2UserService oAuth2UserService, HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository,
						  CustomCorsConfiguration customCorsConfiguration) {
		this.rsaKeyConfigProperties = rsaKeyConfigProperties;
		this.authServicImpl = authServicImpl;
		this.oAuth2UserService = oAuth2UserService;
		this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
		this.customCorsConfiguration = customCorsConfiguration;
	}

	@Bean
	public AuthenticationManager authManager() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(authServicImpl);
		authProvider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(authProvider);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
		return http
				.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.configurationSource(customCorsConfiguration.corsConfigurationSource()))
				.authorizeHttpRequests(auth -> auth
						// Public access URLs
						.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/v1/auth/**", "/auth/**",
								"/oauth2/**", "/send-email", "/api/public/**", "/api/payments/**", "/api/course-progress/**",
								"/api/users/**", "/api/media/**", "/user/me", "/login/oauth2/**", "/oauth2/callback/google").permitAll()
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
						// Student access to ticket endpoints
						.requestMatchers("/api/tickets//student/{studentId}", "/api/tickets").hasAuthority("SCOPE_ROLE_GUEST")

						// Teacher access to course related endpoints
						.requestMatchers("/api/courses", "/api/courses/**", "/api/modules/{moduleId}/lessons/**",
								"/api/modules/{moduleId}/lessons", "/api/courses/{courseId}/offers/**",
								"/api/courses/{courseId}/modules/{moduleId}").hasAuthority("SCOPE_ROLE_TEACHER")

						// Admin access to user management and ticket resolution
						.requestMatchers("/api/users/admin/**", "/api/tickets/**").hasAuthority("SCOPE_ROLE_ADMIN")

						// Default rule: any other request requires authentication
						.anyRequest().authenticated())
				.oauth2Login(
						oauth2 -> oauth2.userInfoEndpoint(infoEndpoint -> infoEndpoint.userService(oAuth2UserService))

								.successHandler(customAuthenticationSuccessHandler())
								.defaultSuccessUrl("http://localhost:3000/role-selection")
								.failureHandler(customAuthenticationFailureHandler())
								.authorizationEndpoint(authEnd -> authEnd.baseUri("/oauth2/authorize")
										.authorizationRequestRepository(cookieAuthorizationRequestRepository()))
								.redirectionEndpoint(authRedir -> authRedir.baseUri("/oauth2/callback/google")))
				.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.oauth2ResourceServer((oauth2) -> oauth2.jwt((jwt) -> jwt.decoder(jwtDecoder())))
				.userDetailsService(authServicImpl).httpBasic(Customizer.withDefaults()).build();
	}


	@Bean
	public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
		return new HttpCookieOAuth2AuthorizationRequestRepository();
	}

	@Bean
	public JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withPublicKey(rsaKeyConfigProperties.publicKey()).build();
	}

	@Bean
	public JwtEncoder jwtEncoder() {
		JWK jwk = new RSAKey.Builder(rsaKeyConfigProperties.publicKey()).privateKey(rsaKeyConfigProperties.privateKey()).build();
		JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
		return new NimbusJwtEncoder(jwks);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SimpleUrlAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
		return new OAuth2AuthenticationSuccessHandler(httpCookieOAuth2AuthorizationRequestRepository, jwtEncoder());
	}

	@Bean
	public SimpleUrlAuthenticationFailureHandler customAuthenticationFailureHandler() {
		return new OAuth2AuthenticationFailureHandler(httpCookieOAuth2AuthorizationRequestRepository);
	}
}
