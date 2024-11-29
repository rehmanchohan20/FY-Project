package com.rehman.elearning.rest.dto.outbound;

import jakarta.validation.constraints.NotBlank;

public class LoginResponseDTO {

	private Long userId;

	@NotBlank
	private String token;

	@NotBlank
	private String username;



	private Boolean isTeacher;
	private Boolean isStudent;
	private Boolean isAdmin;


	public LoginResponseDTO() {
	}

	public LoginResponseDTO(@NotBlank String token, @NotBlank String username) {
		this.token = token;
		this.username = username;
	}

	public LoginResponseDTO(Long userId,String token, String username, Boolean isTeacher, Boolean isStudent, Boolean isAdmin) {
		this.token = token;
		this.username = username;
		this.isTeacher = isTeacher;
		this.isStudent = isStudent;
		this.isAdmin = isAdmin;
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getIsTeacher() {
		return isTeacher;
	}

	public void setIsTeacher(Boolean isTeacher) {
		this.isTeacher = isTeacher;
	}

	public Boolean getIsStudent() {
		return isStudent;
	}

	public void setIsStudent(Boolean isStudent) {
		this.isStudent = isStudent;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}