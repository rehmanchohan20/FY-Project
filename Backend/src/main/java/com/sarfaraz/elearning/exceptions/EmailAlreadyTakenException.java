package com.sarfaraz.elearning.exceptions;

import com.sarfaraz.elearning.constants.ErrorEnum;

public class EmailAlreadyTakenException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailAlreadyTakenException(ErrorEnum errorEnum) {
		super(errorEnum.getCode(), errorEnum.getDescription());
	}

}
