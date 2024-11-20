package com.rehman.elearning.rest.dto.inbound;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public class RegistrationAdminRequestDTO {

	@NotBlank
	private String username;

	@NotBlank
	private String email;

	@NotBlank
	private String password;

	@NotNull // Ensures the value is explicitly provided in the request
	private Boolean isTeacher;

	public RegistrationAdminRequestDTO() {
		super();
	}

	public RegistrationAdminRequestDTO(String username, String email, String password, Boolean isTeacher) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.isTeacher = isTeacher;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsTeacher() {
		return isTeacher;
	}

	public void setIsTeacher(Boolean isTeacher) {
		this.isTeacher = isTeacher;
	}

	@Override
	public String toString() {
		return "RegistrationRequestDTO{" +
				"username='" + username + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", isTeacher=" + isTeacher + '}';
	}
}
