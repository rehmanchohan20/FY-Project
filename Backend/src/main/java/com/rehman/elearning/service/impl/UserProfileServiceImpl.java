package com.rehman.elearning.service.impl;

import com.rehman.elearning.model.User;
import com.rehman.elearning.repository.UserRepository;
import com.rehman.elearning.rest.dto.inbound.UserProfileRequestDTO;
import com.rehman.elearning.rest.dto.outbound.UserProfileResponseDTO;
import com.rehman.elearning.service.UserProfileService;
import org.springframework.stereotype.Service;


@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserRepository userRepository;

    public UserProfileServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserProfileResponseDTO getUserProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToResponseDTO(user);
    }

    @Override
    public UserProfileResponseDTO updateUserProfile(String username, UserProfileRequestDTO requestDTO) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFullName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());
        user.setImage(requestDTO.getProfilePicture());

        User updatedUser = userRepository.save(user);
        return mapToResponseDTO(updatedUser);
    }

    private UserProfileResponseDTO mapToResponseDTO(User user) {
        UserProfileResponseDTO responseDTO = new UserProfileResponseDTO();
        responseDTO.setName(user.getName());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setProfilePicture(user.getImage());
        return responseDTO;
    }
}
