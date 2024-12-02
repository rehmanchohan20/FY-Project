package com.rehman.elearning.config;

import com.nimbusds.jose.jwk.*;
import com.nimbusds.jose.jwk.source.*;
import com.nimbusds.jose.proc.SecurityContext;
import com.rehman.elearning.repository.UserRepository;
import com.rehman.elearning.service.auth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.rehman.elearning.service.auth.OAuth2AuthenticationFailureHandler;
import com.rehman.elearning.service.auth.OAuth2AuthenticationSuccessHandler;
import com.rehman.elearning.service.auth.OAuth2UserService;
import com.rehman.elearning.util.ApiUrlListUtil;
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
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import com.rehman.elearning.service.auth.*;
import com.rehman.elearning.service.impl.AuthServicImpl;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private RsaKeyConfigProperties rsaKeyConfigProperties;
	private AuthServicImpl authServicImpl;
	private OAuth2UserService oAuth2UserService;
	private CustomCorsConfiguration customCorsConfiguration;
	private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
	private UserRepository userRepository;
	@Autowired
	public SecurityConfig(RsaKeyConfigProperties rsaKeyConfigProperties, AuthServicImpl authServicImpl,
						  OAuth2UserService oAuth2UserService, HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository,
						  CustomCorsConfiguration customCorsConfiguration,UserRepository userRepository) {
		this.rsaKeyConfigProperties = rsaKeyConfigProperties;
		this.authServicImpl = authServicImpl;
		this.oAuth2UserService = oAuth2UserService;
		this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
		this.customCorsConfiguration = customCorsConfiguration;
		this.userRepository=userRepository;
	}

	@Bean
	public AuthenticationManager authManager() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(authServicImpl);
		authProvider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(authProvider);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector, InMemoryClientRegistrationRepository clientRegistrationRepository) throws Exception {
		return http
				.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.configurationSource(customCorsConfiguration.corsConfigurationSource()))
				.authorizeHttpRequests(auth -> auth
						// Public access URLs
						.requestMatchers(ApiUrlListUtil.getApiIgnoreUrlList().toArray(new RequestMatcher[0])).permitAll()
						// Student access to ticket endpoints
								.requestMatchers(ApiUrlListUtil.getStudentApiUrls().toArray(new RequestMatcher[0])).hasAuthority("SCOPE_ROLE_STUDENT")
						//Guest Related end points
								.requestMatchers(ApiUrlListUtil.getGuestApiUrls().toArray(new RequestMatcher[0])).hasAuthority("SCOPE_ROLE_GUEST")
						// Teacher access to course related endpoints
								.requestMatchers(ApiUrlListUtil.getTeacherApiUrls().toArray(new RequestMatcher[0])).hasAuthority("SCOPE_ROLE_TEACHER")
                                .requestMatchers("api/media/upload-media").hasAuthority("ROLE_TEACHER")
						//Common permission for multiple roles
								.requestMatchers(ApiUrlListUtil.getCommonApiURLS().toArray(new RequestMatcher[0])).hasAnyAuthority("SCOPE_ROLE_TEACHER","SCOPE_ROLE_STUDENT","SCOPE_ROLE_ADMIN")
						// Admin access to user management and ticket resolution
						.requestMatchers(ApiUrlListUtil.getAdminApiUrls().toArray(new RequestMatcher[0])).hasAuthority("SCOPE_ROLE_ADMIN")
								.anyRequest()
								.authenticated() // All other requests require authentication
						// Default rule: any other request requires authentication
						)
				.oauth2Login(
						oauth2 -> oauth2.userInfoEndpoint(infoEndpoint -> infoEndpoint.userService(oAuth2UserService))
								.successHandler(customAuthenticationSuccessHandler())
								.failureHandler(customAuthenticationFailureHandler())
								.authorizationEndpoint(authEnd -> authEnd.baseUri("/oauth2/authorize")
										.authorizationRequestRepository(cookieAuthorizationRequestRepository())
								)
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
		return new OAuth2AuthenticationSuccessHandler(httpCookieOAuth2AuthorizationRequestRepository, jwtEncoder(),userRepository);
	}

	@Bean
	public SimpleUrlAuthenticationFailureHandler customAuthenticationFailureHandler() {
		return new OAuth2AuthenticationFailureHandler(httpCookieOAuth2AuthorizationRequestRepository);
	}
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
