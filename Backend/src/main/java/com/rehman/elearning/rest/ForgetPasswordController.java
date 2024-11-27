package com.rehman.elearning.rest;

import com.rehman.elearning.rest.dto.inbound.OtpValidationRequestDTO;
import com.rehman.elearning.rest.dto.inbound.ResetPasswordRequestDTO;
import com.rehman.elearning.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class ForgetPasswordController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/forgot")
    public ResponseEntity<String> forgotPassword(@RequestBody ResetPasswordRequestDTO requestDTO) {
        return ResponseEntity.ok(userService.forgotPassword(
                requestDTO.getEmail()
        ));
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequestDTO requestDTO) {
        return ResponseEntity.ok(userService.setPassword(
                requestDTO.getEmail(),
                requestDTO.getOtp(),
                requestDTO.getNewPassword()
        ));
    }

    @PostMapping("/regenerate-otp")
    public ResponseEntity<String> regenerateOtp(@RequestBody ResetPasswordRequestDTO requestDTO) {
        return ResponseEntity.ok(userService.regenerateOtp(
                requestDTO.getEmail()
        ));
    }


    // Endpoint for OTP validation
    @PostMapping("/validate-otp")
    public ResponseEntity<String> validateOtp(@RequestBody OtpValidationRequestDTO otpValidationRequestDTO) {
        try {
            boolean isValid = userService.validateOtp(otpValidationRequestDTO.getEmail(), otpValidationRequestDTO.getOtp());
            if (isValid) {
                return ResponseEntity.ok("OTP verified successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP or OTP expired. Please regenerate OTP.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error validating OTP: " + e.getMessage());
        }
    }


}

