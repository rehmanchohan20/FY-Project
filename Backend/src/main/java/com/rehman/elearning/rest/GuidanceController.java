package com.rehman.elearning.rest;

import com.rehman.elearning.model.User;
import com.rehman.elearning.repository.UserRepository;
import com.rehman.elearning.rest.dto.inbound.GuidanceRequestDTO;
import com.rehman.elearning.rest.dto.outbound.GuidanceResponseDTO;
import com.rehman.elearning.service.GuidanceService;
import com.rehman.elearning.service.impl.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guidance")
public class GuidanceController {

    @Autowired
    private GuidanceService guidanceService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseServiceImpl courseService;

    @PostMapping
    public GuidanceResponseDTO provideGuidance(@RequestBody GuidanceRequestDTO guidanceRequestDTO,
                                               @AuthenticationPrincipal Jwt jwt) {
        // Attempt to retrieve the user's ID from JWT claims
        String userIdString = jwt.getClaim("jti");  // 'jti' is expected to be a String in your JWT

        Long userId = null;
        try {
            // Convert the String userId to Long
            userId = Long.parseLong(userIdString);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("User ID in JWT is not a valid Long");
        }

        Long studentId = null;

        // Fetch the User entity based on the extracted userId
        if (userId != null) {
            User user = userRepository.findById(userId).orElse(null);  // Fetch user by ID
            if (user != null && user.isStudent()) {
                studentId = user.getStudent().getUserId();  // Fetching the student's userId
            }
        }

        if (studentId == null) {
            throw new IllegalStateException("Student ID not found in JWT or user is not a student");
        }

        // Pass the studentId to your service and return a response
        return guidanceService.provideGuidance(guidanceRequestDTO, studentId);
    }
}



//package com.sarfaraz.elearning.rest;
//
//import com.sarfaraz.elearning.model.Course;
//import com.sarfaraz.elearning.model.User;
//import com.sarfaraz.elearning.repository.GuidanceRepository;
//import com.sarfaraz.elearning.repository.UserRepository;
//import com.sarfaraz.elearning.rest.dto.inbound.GuidanceRequestDTO;
//import com.sarfaraz.elearning.rest.dto.outbound.GuidanceResponseDTO;
//import com.sarfaraz.elearning.service.GuidanceService;
//import com.sarfaraz.elearning.service.impl.CourseServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/guidance")
//public class GuidanceController {
//
//    @Autowired
//    private GuidanceService guidanceService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private CourseServiceImpl courseService;
//
//    @PostMapping
//    public GuidanceResponseDTO provideGuidance(@RequestBody GuidanceRequestDTO guidanceRequestDTO,
//                                               @AuthenticationPrincipal Jwt jwt) {
//        // Attempt to retrieve the user's ID from JWT claims
//        String userIdString = jwt.getClaim("jti");  // 'jti' is expected to be a String in your JWT
//
//        Long userId = null;
//        try {
//            // Convert the String userId to Long
//            userId = Long.parseLong(userIdString);
//        } catch (NumberFormatException e) {
//            throw new IllegalStateException("User ID in JWT is not a valid Long");
//        }
//
//        Long studentId = null;
//
//        // Fetch the User entity based on the extracted userId
//        if (userId != null) {
//            User user = userRepository.findById(userId).orElse(null);  // Fetch user by ID
//            if (user != null && user.isStudent()) {
//                studentId = user.getStudent().getUserId();  // Fetching the student's userId
//            }
//        }
//
//        if (studentId == null) {
//            throw new IllegalStateException("Student ID not found in JWT or user is not a student");
//        }
//
//        // Pass the studentId to your service
//        return guidanceService.provideGuidance(guidanceRequestDTO, studentId);
//    }
//
//    @GetMapping("/search")
//    public List<Course> searchCourses(@RequestParam String keyword) {
//        return courseService.getCoursesByKeyword(keyword);
//    }
//
//
//}
//
