package com.rehman.elearning.rest;

import com.rehman.elearning.rest.dto.inbound.RegistrationAdminRequestDTO;
import com.rehman.elearning.rest.dto.inbound.UserRequestDTO;
import com.rehman.elearning.rest.dto.outbound.RegistrationAdminResponseDTO;
import com.rehman.elearning.rest.dto.outbound.UserResponseDTO;
import com.rehman.elearning.service.UserService;
import com.rehman.elearning.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;



@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<RegistrationAdminResponseDTO>> registerUserByAdmin(
            @RequestBody @Valid RegistrationAdminRequestDTO registrationAdminRequestDTO) {
        RegistrationAdminResponseDTO response = userService.registerUserByAdmin(registrationAdminRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<RegistrationAdminResponseDTO>(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@AuthenticationPrincipal Jwt jwt) {
        UserResponseDTO user = userService.getUserById(Long.valueOf(jwt.getId()));
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<UserResponseDTO> updateUser(@AuthenticationPrincipal Jwt jwt, @RequestBody UserRequestDTO userRequestDTO) throws IOException {
        UserResponseDTO user = userService.updateUser(Long.valueOf(jwt.getId()), userRequestDTO);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> updateUserByAdmin(@PathVariable Long userId, @RequestBody UserRequestDTO userRequestDTO) throws IOException {
        UserResponseDTO user = userService.updateUserByAdmin(userId, userRequestDTO);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

}

