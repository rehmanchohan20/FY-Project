package com.rehman.elearning.service;

import com.rehman.elearning.rest.dto.inbound.UserProfileRequestDTO;
import com.rehman.elearning.rest.dto.outbound.UserProfileResponseDTO;

public interface UserProfileService {
    UserProfileResponseDTO getUserProfile(Long userId);
    UserProfileResponseDTO updateUserProfile(String username, UserProfileRequestDTO requestDTO);
}