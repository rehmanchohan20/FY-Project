package com.rehman.elearning.exceptions;

import com.rehman.elearning.constants.ErrorEnum;

public class EmailAlreadyTakenException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailAlreadyTakenException(ErrorEnum errorEnum) {
		super(errorEnum.getCode(), errorEnum.getDescription());
	}

}
