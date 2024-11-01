package com.sarfaraz.elearning.constants;

public enum ErrorEnum {

	INVALID_CREDENTIALS(GeneralErrorEnum.FAILURE.getCode(), "Incorrect username or password"),
	USERNAME_ALREADY_EXIST(GeneralErrorEnum.FAILURE.getCode(), "Username already taken"),
	EMAIL_ALREADY_EXIST(GeneralErrorEnum.FAILURE.getCode(), "Email already taken"),
	UNSUPPORTED_SSO_LOGIN(GeneralErrorEnum.FAILURE.getCode(), "Sorry! supported sso login"),
	SSO_ACCOUNT_NOT_FOUND(GeneralErrorEnum.FAILURE.getCode(), "Email not found from OAuth2 provider"),
	SSO_UNAUTHORIZAED_REDIRECT(GeneralErrorEnum.FAILURE.getCode(),
			"Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication"),
	USER_NOT_FOUND(GeneralErrorEnum.FAILURE.getCode(), "User not found"),
	RESOURCE_NOT_FOUND(GeneralErrorEnum.FAILURE.getCode(), "Resource not found"),
	TEACHER_NOT_FOUND(GeneralErrorEnum.FAILURE.getCode(), "Teacher not found"),
	COURSE_NOT_FOUND(GeneralErrorEnum.FAILURE.getCode(), "Course not found"),
	MEDIA_NOT_FOUND(GeneralErrorEnum.FAILURE.getCode(), "Media not found"),
	STUDENT_NOT_FOUND(GeneralErrorEnum.FAILURE.getCode(), "Student not found"),
	LESSON_NOT_FOUND(GeneralErrorEnum.FAILURE.getCode(), "Lesson not found"),
	MODULE_NOT_FOUND(GeneralErrorEnum.FAILURE.getCode(), "Module not found"),;

	private String code;
	private String description;

	ErrorEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

}
