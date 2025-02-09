package com.rehman.elearning.service;

import com.rehman.elearning.constants.RoleEnum;
import com.rehman.elearning.exceptions.ApplicationException;
import com.rehman.elearning.model.User;
import com.rehman.elearning.rest.dto.inbound.*;
import com.rehman.elearning.rest.dto.outbound.LoginResponseDTO;
import com.rehman.elearning.rest.dto.outbound.RegistrationAdminResponseDTO;
import com.rehman.elearning.rest.dto.outbound.RegistrationResponseDTO;
import com.rehman.elearning.rest.dto.outbound.UserResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

	public LoginResponseDTO userAuthenticate(LoginRequestDTO loginRequestDTO) throws ApplicationException;

	public RegistrationResponseDTO userRegistration(RegistrationRequestDTO registrationRequestDTO)
			throws ApplicationException;


	public UserResponseDTO registerUserByAdmin(UserRequestDTO  userRequestDTO) throws ApplicationException;
	UserResponseDTO updateUser(Long id, UserProfileRequestDTO userRequestDto) throws IOException;
	UserResponseDTO updateUserByAdmin(Long userId, UserRequestDTO userRequestDto) throws IOException;
	UserResponseDTO getUserById(Long id);
	List<UserResponseDTO> getAllUsers();
	void deleteUser(Long id);
	public String forgotPassword(String email);
	public String setPassword(String email, String newPassword);
	public String regenerateOtp(String email);
	public String setPassword(String email, String otp, String newPassword);
	public boolean validateOtp(String email, String otp);

}
