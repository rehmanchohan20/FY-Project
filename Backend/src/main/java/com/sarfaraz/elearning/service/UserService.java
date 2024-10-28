package com.sarfaraz.elearning.service;

import com.sarfaraz.elearning.exceptions.ApplicationException;
import com.sarfaraz.elearning.rest.dto.inbound.LoginRequestDTO;
import com.sarfaraz.elearning.rest.dto.inbound.RegistrationAdminRequestDTO;
import com.sarfaraz.elearning.rest.dto.inbound.RegistrationRequestDTO;
import com.sarfaraz.elearning.rest.dto.inbound.UserRequestDTO;
import com.sarfaraz.elearning.rest.dto.outbound.LoginResponseDTO;
import com.sarfaraz.elearning.rest.dto.outbound.RegistrationAdminResponseDTO;
import com.sarfaraz.elearning.rest.dto.outbound.RegistrationResponseDTO;
import com.sarfaraz.elearning.rest.dto.outbound.UserResponseDTO;

import java.util.List;

public interface UserService {

	public LoginResponseDTO userAuthenticate(LoginRequestDTO loginRequestDTO) throws ApplicationException;

	public RegistrationResponseDTO userRegistration(RegistrationRequestDTO registrationRequestDTO)
			throws ApplicationException;


	public RegistrationAdminResponseDTO registerUserByAdmin(RegistrationAdminRequestDTO registrationAdminRequestDTO)
			throws ApplicationException;
	UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDto);
	UserResponseDTO getUserById(Long id);
	List<UserResponseDTO> getAllUsers();
	void deleteUser(Long id);

	public String forgotPassword(String email);
	public String setPassword(String email, String newPassword);
	public String regenerateOtp(String email);
}
