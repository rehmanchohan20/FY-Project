package com.sarfaraz.elearning.service.auth;

import java.util.Map;

import com.sarfaraz.elearning.constants.AuthProviderEnum;
import com.sarfaraz.elearning.constants.ErrorEnum;
import com.sarfaraz.elearning.exceptions.OAuth2AuthenticationProcessingException;

public class OAuth2UserInfoFactory {
	public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
		if (registrationId.equalsIgnoreCase(AuthProviderEnum.google.toString())) {
			return new GoogleOAuth2UserInfo(attributes);
		} else {
			throw new OAuth2AuthenticationProcessingException(ErrorEnum.UNSUPPORTED_SSO_LOGIN);
		}
	}
}
