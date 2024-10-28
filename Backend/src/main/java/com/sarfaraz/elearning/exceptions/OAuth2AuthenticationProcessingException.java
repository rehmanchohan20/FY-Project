package com.sarfaraz.elearning.exceptions;

import com.sarfaraz.elearning.constants.ErrorEnum;

public class OAuth2AuthenticationProcessingException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OAuth2AuthenticationProcessingException(ErrorEnum errorEnum) {
		super(errorEnum.getCode(), errorEnum.getDescription());
	}

}
