package com.rehman.elearning.constants;

public enum GeneralErrorEnum {
	SUCCESS("200"), FAILURE("500");

	private String code;

	GeneralErrorEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
