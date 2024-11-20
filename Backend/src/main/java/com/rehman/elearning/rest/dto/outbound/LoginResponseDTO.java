package com.rehman.elearning.rest.dto.outbound;

import jakarta.validation.constraints.NotBlank;

public class LoginResponseDTO {

	@NotBlank
	private String token;

	@NotBlank
	private String username;

	public LoginResponseDTO() {
		super();
	}

	public LoginResponseDTO(@NotBlank String token, @NotBlank String username) {
		super();
		this.token = token;
		this.username = username;
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
}


//package com.rehman.elearning.rest.dto.outbound;
//
//import jakarta.validation.constraints.NotBlank;
//
//public class LoginResponseDTO {
//
//	@NotBlank
//	private String token;
//
//	@NotBlank
//	private Boolean isTeacher;
//
//	@NotBlank
//	private Boolean isAdmin;
//
//	public LoginResponseDTO() {
//		super();
//	}
//
//	public LoginResponseDTO(@NotBlank String token, @NotBlank Boolean isTeacher, @NotBlank Boolean isAdmin) {
//		super();
//		this.token = token;
//		this.isTeacher = isTeacher;
//		this.isAdmin = isAdmin;
//	}
//
//	public String getToken() {
//		return token;
//	}
//
//	public void setToken(String token) {
//		this.token = token;
//	}
//
//	public Boolean getIsTeacher() {
//		return isTeacher;
//	}
//
//	public void setIsTeacher(Boolean isTeacher) {
//		this.isTeacher = isTeacher;
//	}
//
//	public Boolean getIsAdmin() {
//		return isAdmin;
//	}
//
//	public void setIsAdmin(Boolean isAdmin) {
//		this.isAdmin = isAdmin;
//	}
//
//}
