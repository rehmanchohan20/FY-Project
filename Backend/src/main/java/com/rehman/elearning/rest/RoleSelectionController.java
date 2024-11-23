package com.rehman.elearning.rest;

import com.rehman.elearning.model.User;
import com.rehman.elearning.rest.dto.inbound.RoleRequestDTO;
import com.rehman.elearning.rest.dto.outbound.UserResponseDTO;
import com.rehman.elearning.service.RoleSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/oauth2/")
public class RoleSelectionController {


    @Autowired
    private RoleSelection roleSelection;

    @PutMapping("/role")
    public ResponseEntity<UserResponseDTO> updateUserRole(@AuthenticationPrincipal Jwt jwt, @RequestBody RoleRequestDTO roleUpdateDTO) {
        User user = roleSelection.updateUserRole(Long.valueOf(jwt.getId()), roleUpdateDTO.getRole());
        UserResponseDTO responseDTO = new UserResponseDTO(user);
        return ResponseEntity.ok(responseDTO);
    }
}
