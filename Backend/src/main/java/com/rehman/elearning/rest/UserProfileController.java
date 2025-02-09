package com.rehman.elearning.rest;

import com.rehman.elearning.rest.dto.inbound.UserProfileRequestDTO;
import com.rehman.elearning.rest.dto.outbound.UserProfileResponseDTO;
import com.rehman.elearning.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    // GET /api/users/me - Get current user profile
    @GetMapping("/me")
    public ResponseEntity<UserProfileResponseDTO> getUserProfile(@AuthenticationPrincipal Jwt jwt) {
        long userId = Long.valueOf(jwt.getId());
        UserProfileResponseDTO responseDTO = userProfileService.getUserProfile(userId);
        return ResponseEntity.ok(responseDTO);
    }

    // PUT /api/users/me - Update current user profile
    @PutMapping("/me")
    public ResponseEntity<UserProfileResponseDTO> updateUserProfile(
            Authentication authentication,
            @RequestBody UserProfileRequestDTO requestDTO) throws IOException {
        String username = authentication.getName();
        UserProfileResponseDTO updatedProfile = userProfileService.updateUserProfile(username, requestDTO);
        return ResponseEntity.ok(updatedProfile);
    }
}