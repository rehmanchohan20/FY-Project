package com.rehman.elearning.rest.dto.outbound;

import jakarta.validation.constraints.NotBlank;

public class RegistrationResponseDTO {

	@NotBlank
	private String userId;

	public RegistrationResponseDTO() {
		super();
	}

	public RegistrationResponseDTO(@NotBlank String userId) {
		super();
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
