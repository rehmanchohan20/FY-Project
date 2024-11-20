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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;



@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("admin")
    public ResponseEntity<ApiResponse<RegistrationAdminResponseDTO>> registerUserByAdmin(
            @RequestBody @Valid RegistrationAdminRequestDTO registrationAdminRequestDTO) {
        RegistrationAdminResponseDTO response = userService.registerUserByAdmin(registrationAdminRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<RegistrationAdminResponseDTO>(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO user = userService.updateUser(id, userRequestDTO);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Endpoint to upload profile picture
    @PostMapping("/{userId}/uploadProfileImage")
    public ResponseEntity<String> uploadProfileImage( @PathVariable Long userId,
                                                     @RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = userService.uploadProfileImage(userId, file);
            return ResponseEntity.ok("Profile image uploaded successfully: " + imageUrl);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error uploading profile image: " + e.getMessage());
        }
    }
}

