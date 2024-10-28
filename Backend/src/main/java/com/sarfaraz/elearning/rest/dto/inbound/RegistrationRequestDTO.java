package com.sarfaraz.elearning.rest.dto.inbound;

import com.sarfaraz.elearning.constants.UserCreatedBy;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Validated
public class RegistrationRequestDTO {

	@NotBlank
	private String username;

	@NotBlank
	private String email;

	@NotBlank
	private String password;

	@NotNull
	private boolean isTeacher;

	@NotBlank
	private String role;

	public @NotBlank String getRole() {
		return role;
	}

	public void setRole(@NotBlank String role) {
		this.role = role;
	}

	public RegistrationRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RegistrationRequestDTO(String username, String email, String password, boolean isTeacher, String role) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.isTeacher = isTeacher;
		this.role = role;
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

	public boolean isTeacher() {
		return isTeacher;
	}

	public void setTeacher(boolean isTeacher) {
		this.isTeacher = isTeacher;
	}
}
