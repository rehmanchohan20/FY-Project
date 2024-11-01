package com.sarfaraz.elearning.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sarfaraz.elearning.util.ApiResponse;
import org.springframework.security.oauth2.jwt.Jwt;

@RestController
@RequestMapping("/user/me")
public class ProfileController {

	@GetMapping
	public ResponseEntity<ApiResponse<String>> home(@AuthenticationPrincipal Jwt jwt) {
		return ResponseEntity.ok().body(new ApiResponse<String>("Home details " + jwt.getId()));
	}

}
