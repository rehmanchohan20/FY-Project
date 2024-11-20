package com.rehman.elearning.exceptions;

import com.rehman.elearning.constants.ErrorEnum;

public class UsernameAlreadyTakenException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsernameAlreadyTakenException(ErrorEnum errorEnum) {
		super(errorEnum.getCode(), errorEnum.getDescription());
	}

}
