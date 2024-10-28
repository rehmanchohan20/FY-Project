package com.sarfaraz.elearning.exceptions;

import com.sarfaraz.elearning.constants.ErrorEnum;

public class IncorrectUsernameAndPasswordException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IncorrectUsernameAndPasswordException(ErrorEnum errorEnum) {
		super(errorEnum.getCode(), errorEnum.getDescription());
	}

}
