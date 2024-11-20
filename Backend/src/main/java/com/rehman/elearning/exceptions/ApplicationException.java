package com.rehman.elearning.exceptions;

import com.rehman.elearning.constants.GeneralErrorEnum;

public abstract class ApplicationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code = GeneralErrorEnum.FAILURE.getCode();
	private String details;

	
	
	public ApplicationException(String code, String details) {
		super(details);
		this.code = code;
		this.details = details;
	}

	public ApplicationException(String message) {
		super(message);
	}

	public String getCode() {
		return code;
	}

	public String getDetails() {
		return details;
	}
}
