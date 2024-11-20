package com.rehman.elearning.rest;

import com.rehman.elearning.exceptions.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rehman.elearning.rest.dto.inbound.LoginRequestDTO;
import com.rehman.elearning.rest.dto.outbound.LoginResponseDTO;
import com.rehman.elearning.service.UserService;
import com.rehman.elearning.util.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/auth")
public class LoginController {

	private UserService userService;

	@Autowired
	public LoginController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/login") // Ensure this doesn't conflict with OAuth2
	@Operation(summary = "Login API", description = "Student, teacher, or admin can log in with traditional method.")
	public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO)
			throws ApplicationException {
		LoginResponseDTO loginResponseDTO = userService.userAuthenticate(loginRequestDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(loginResponseDTO));
	}
}
