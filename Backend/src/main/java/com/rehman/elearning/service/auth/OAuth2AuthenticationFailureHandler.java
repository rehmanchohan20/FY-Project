package com.rehman.elearning.service.auth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.util.UriComponentsBuilder;

import com.rehman.elearning.util.CookieUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.rehman.elearning.service.auth.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

	@Autowired
	public OAuth2AuthenticationFailureHandler(
			HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository) {
		super();
		this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String targetUrl = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME).map(Cookie::getValue)
				.orElse(("/"));

		targetUrl = UriComponentsBuilder.fromUriString(targetUrl).queryParam("error", exception.getLocalizedMessage())
				.build().toUriString();

		httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequest(request, response);

		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

}
