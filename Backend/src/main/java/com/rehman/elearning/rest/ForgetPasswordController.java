package com.rehman.elearning.rest;

import com.rehman.elearning.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class ForgetPasswordController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/forgot")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        return ResponseEntity.ok(userService.forgotPassword(email));
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestParam String email, @RequestParam String otp, @RequestParam String newPassword) {
        return ResponseEntity.ok(userService.setPassword(email, otp, newPassword));
    }

    @PostMapping("/regenerate-otp")
    public ResponseEntity<String> regenerateOtp(@RequestParam String email) {
        return ResponseEntity.ok(userService.regenerateOtp(email));
    }
}
