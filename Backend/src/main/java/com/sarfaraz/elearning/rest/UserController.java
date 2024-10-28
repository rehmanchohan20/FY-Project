package com.sarfaraz.elearning.rest;

import com.sarfaraz.elearning.rest.dto.inbound.RegistrationAdminRequestDTO;
import com.sarfaraz.elearning.rest.dto.inbound.UserRequestDTO;
import com.sarfaraz.elearning.rest.dto.outbound.RegistrationAdminResponseDTO;
import com.sarfaraz.elearning.rest.dto.outbound.UserResponseDTO;
import com.sarfaraz.elearning.service.UserService;
import com.sarfaraz.elearning.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/admin/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
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
}
//    @PostMapping()
//    public ResponseEntity<ApiResponse<RegistrationResponseDTO>> registration(
//            @RequestBody @Valid RegistrationRequestDTO) {
//        RegistrationResponseDTO responseDTO = userService.userRegistration(registrationRequestDTO);
//        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<RegistrationResponseDTO>(responseDTO));
//}
