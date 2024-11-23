 package com.rehman.elearning.service.auth;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.rehman.elearning.model.User;
import com.rehman.elearning.repository.UserRepository;
import com.rehman.elearning.util.AuthorityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

import com.rehman.elearning.constants.ErrorEnum;
import com.rehman.elearning.exceptions.OAuth2AuthenticationProcessingException;
import com.rehman.elearning.util.CookieUtil;
import com.rehman.elearning.util.JWKGenerateUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.rehman.elearning.service.auth.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
	private JwtEncoder jwtEncoder;
	private UserRepository userRepository;

	@Value("${app.ui.redirect.url}")
	private String uiRedirectUrl;

	public OAuth2AuthenticationSuccessHandler(
			HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository,
			JwtEncoder jwtEncoder,UserRepository userRepository) {
		this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
		this.jwtEncoder = jwtEncoder;
		this.userRepository=userRepository;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		String targetUrl = determineTargetUrl(request, response, authentication);

		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}

		clearAuthenticationAttributes(request, response);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {
		Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
				.map(Cookie::getValue);

		if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
			throw new OAuth2AuthenticationProcessingException(ErrorEnum.SSO_UNAUTHORIZAED_REDIRECT);
		}

		String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
		authentication = updateUserRoleAndAuthentication(authentication);
		String token = JWKGenerateUtil.generateToken(authentication, jwtEncoder);

		return UriComponentsBuilder.fromUriString(targetUrl).queryParam("token", token).queryParam("role",AuthorityUtils.getUserRole(authentication.getAuthorities())).build().toUriString();
	}

	public Authentication updateUserRoleAndAuthentication(Authentication authentication) {
		// Convert role to authority (SimpleGrantedAuthority)
		User user = getUser(authentication);
		user = userRepository.findById(user.getId()).get();
		List<SimpleGrantedAuthority> authorities = AuthorityUtils.convertToSimpleGrantedAuthorities(user.getAuthorities());
		// Create a new authentication object with updated authorities
		Authentication updatedAuthentication =                              new UsernamePasswordAuthenticationToken(
				authentication.getPrincipal(),
				 null,  // OAuth2 does not require password
				authorities
		);
		// Update the security context with the new Authentication object
		SecurityContextHolder.getContext().setAuthentication(updatedAuthentication);
		return updatedAuthentication;
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
		super.clearAuthenticationAttributes(request);
		httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);

	}

	private boolean isAuthorizedRedirectUri(String uri) {
		URI clientRedirectUri = URI.create(uri);
		URI authorizedURI = URI.create(uiRedirectUrl);
		// Only validate host and port. Let the clients use different paths if they want
		if (authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
				&& authorizedURI.getPort() == clientRedirectUri.getPort()) {
			return true;
		}
		return false;
	}
	private User getUser(Authentication authentication){
		User user = (User) authentication.getPrincipal();
		return user;
	}
}
