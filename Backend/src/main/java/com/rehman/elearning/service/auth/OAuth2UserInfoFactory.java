package com.rehman.elearning.service.auth;

import java.util.Map;

import com.rehman.elearning.constants.AuthProviderEnum;
import com.rehman.elearning.constants.ErrorEnum;
import com.rehman.elearning.exceptions.OAuth2AuthenticationProcessingException;

public class OAuth2UserInfoFactory {
	public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
		if (registrationId.equalsIgnoreCase(AuthProviderEnum.google.toString())) {
			return new GoogleOAuth2UserInfo(attributes);
		} else {
			throw new OAuth2AuthenticationProcessingException(ErrorEnum.UNSUPPORTED_SSO_LOGIN);
		}
	}
}
