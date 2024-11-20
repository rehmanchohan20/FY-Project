package com.rehman.elearning.constants;

public enum CourseStatusEnum {
	DRAFT("draft"),REVIEW("review"),PUBLISHED("published");
	private String code;
	CourseStatusEnum(String code){
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
}
