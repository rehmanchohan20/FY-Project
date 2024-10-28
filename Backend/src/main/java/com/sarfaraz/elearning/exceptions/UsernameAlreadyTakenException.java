package com.sarfaraz.elearning.exceptions;

import com.sarfaraz.elearning.constants.ErrorEnum;

public class UsernameAlreadyTakenException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsernameAlreadyTakenException(ErrorEnum errorEnum) {
		super(errorEnum.getCode(), errorEnum.getDescription());
	}

}
