package com.sarfaraz.elearning.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import com.sarfaraz.elearning.rest.dto.inbound.RegistrationRequestDTO;
import com.sarfaraz.elearning.rest.dto.outbound.RegistrationResponseDTO;
import com.sarfaraz.elearning.service.UserService;
import com.sarfaraz.elearning.util.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/auth")
public class RegistrationController {

	public UserService userService;

	@Autowired
	public RegistrationController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("registration")
	@Operation(summary = "Registration API", description = "register student or teacher")
	public ResponseEntity<ApiResponse<RegistrationResponseDTO>> registration(
			@RequestBody @Valid RegistrationRequestDTO registrationRequestDTO) {
		RegistrationResponseDTO responseDTO = userService.userRegistration(registrationRequestDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<RegistrationResponseDTO>(responseDTO));
	}

}