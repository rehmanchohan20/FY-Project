package com.rehman.elearning.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.rehman.elearning.constants.AuthProviderEnum;
import com.rehman.elearning.constants.ErrorEnum;
import com.rehman.elearning.constants.UserCreatedBy;
import com.rehman.elearning.exceptions.*;
import com.rehman.elearning.rest.dto.inbound.LoginRequestDTO;
import com.rehman.elearning.rest.dto.inbound.RegistrationAdminRequestDTO;
import com.rehman.elearning.rest.dto.inbound.RegistrationRequestDTO;
import com.rehman.elearning.rest.dto.inbound.UserRequestDTO;
import com.rehman.elearning.exceptions.*;
import com.rehman.elearning.model.Student;
import com.rehman.elearning.model.Teacher;
import com.rehman.elearning.model.User;
import com.rehman.elearning.repository.UserRepository;
import com.rehman.elearning.rest.dto.inbound.*;
import com.rehman.elearning.rest.dto.outbound.LoginResponseDTO;
import com.rehman.elearning.rest.dto.outbound.RegistrationAdminResponseDTO;
import com.rehman.elearning.rest.dto.outbound.RegistrationResponseDTO;
import com.rehman.elearning.rest.dto.outbound.UserResponseDTO;
import com.rehman.elearning.service.UserService;
import com.rehman.elearning.util.EmailUtil;
import com.rehman.elearning.util.JWKGenerateUtil;
import com.rehman.elearning.util.OtpUtil;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

	private JwtEncoder jwtEncoder;
	private AuthenticationManager authenticationManager;
	private PasswordEncoder passwordEncoder;
	private UserRepository userRepository;

	@Autowired
	private OtpUtil otpUtil;

	@Autowired
	private EmailUtil emailUtil;

	@Autowired
	private Cloudinary cloudinary;

//    @Autowired
//	private AuditorAware<UserCreatedBy> auditorAware;

	@Autowired
	public UserServiceImpl(JwtEncoder jwtEncoder, AuthenticationManager authenticationManager,
						   PasswordEncoder passwordEncoder, UserRepository userRepository) {
		this.jwtEncoder = jwtEncoder;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public LoginResponseDTO userAuthenticate(LoginRequestDTO loginRequestDTO) throws ApplicationException {
		String token;
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			token = JWKGenerateUtil.generateToken(authentication, jwtEncoder);
		} catch (Exception e) {
			throw new IncorrectUsernameAndPasswordException(ErrorEnum.INVALID_CREDENTIALS);
		}

		User user = userRepository.findByUsernameOrEmail(loginRequestDTO.getUsername()).orElseThrow(
				() -> new UserNotFoundException(ErrorEnum.USER_NOT_FOUND)
		);
		// Return the token and username
		return new LoginResponseDTO(token, user.getUsername());
	}


	@Override
	@Transactional
	public RegistrationResponseDTO userRegistration(RegistrationRequestDTO registrationRequestDTO) throws ApplicationException {
		// Check for existing email or username
		userRepository.findByEmail(registrationRequestDTO.getEmail()).ifPresent(user -> {
			throw new EmailAlreadyTakenException(ErrorEnum.EMAIL_ALREADY_EXIST);
		});
		userRepository.findByUsername(registrationRequestDTO.getUsername()).ifPresent(user -> {
			throw new UsernameAlreadyTakenException(ErrorEnum.USERNAME_ALREADY_EXIST);
		});

		// Create a new User instance and set its attributes
		User user = new User();
		user.setUsername(registrationRequestDTO.getUsername());
		user.setFullName(registrationRequestDTO.getUsername()); // Assuming fullName is the same as username
		user.setPassword(passwordEncoder.encode(registrationRequestDTO.getPassword()));
		user.setEmail(registrationRequestDTO.getEmail());
		user.setCreatedBy(UserCreatedBy.Self);
		user.setAdmin(false); // Default value for isAdmin
		user.setImage("default-profile.png"); // Default image URL
		user.setAuthProvider(AuthProviderEnum.TraditionalLogin); // Assuming traditional login for registration
		user.setProviderId(UUID.randomUUID().toString()); // Generate a unique provider ID

		// Role assignment based on registration request
		if (registrationRequestDTO.getIsTeacher()) {
			Teacher teacher = new Teacher();
			teacher.setUser(user);
			user.setTeacher(teacher);
			teacher.setCreatedBy(UserCreatedBy.Self);
		} else {
			Student student = new Student();
			student.setUser(user);
			user.setStudent(student);
			student.setCreatedBy(UserCreatedBy.Self);
		}

		userRepository.save(user);
		Optional<User> savedUser = userRepository.findById(user.getId()); savedUser.ifPresent(u -> { System.out.println("Saved user: " + u.getUsername() + ", Role: " + (u.isTeacher() ? "Teacher" : (u.isStudent() ? "Student" : "Guest"))); });
		return new RegistrationResponseDTO(user.getId().toString());
	}

	@Override
	@Transactional
	public RegistrationAdminResponseDTO registerUserByAdmin(RegistrationAdminRequestDTO registrationAdminRequestDTO)
			throws ApplicationException {
		// Check for existing email
		userRepository.findByEmail(registrationAdminRequestDTO.getEmail()).ifPresent(user -> {
			throw new EmailAlreadyTakenException(ErrorEnum.EMAIL_ALREADY_EXIST);
		});

		// Check for existing username
		userRepository.findByUsername(registrationAdminRequestDTO.getUsername()).ifPresent(user -> {
			throw new UsernameAlreadyTakenException(ErrorEnum.USERNAME_ALREADY_EXIST);
		});

		// Create new user
		User user = new User();
		user.setUsername(registrationAdminRequestDTO.getUsername());
		user.setFullName(registrationAdminRequestDTO.getUsername());
		user.setPassword(passwordEncoder.encode(registrationAdminRequestDTO.getPassword()));
		user.setEmail(registrationAdminRequestDTO.getEmail());
		user.setAdmin(false); // Set isAdmin to true since this user is created by admin
		user.setImage("default-profile.png"); // Default image URL
		user.setAuthProvider(AuthProviderEnum.TraditionalLogin);
		user.setProviderId(UUID.randomUUID().toString());
		user.setCreatedBy(UserCreatedBy.Admin); // Set createdBy to "Admin"


		// Role assignment based on registration request
		if (registrationAdminRequestDTO.getIsTeacher()) {
			Teacher teacher = new Teacher();
			teacher.setUser(user);
			teacher.setCreatedBy(UserCreatedBy.Admin);
			user.setTeacher(teacher);
		} else {
			Student student = new Student();
			student.setUser(user);
			student.setCreatedBy(UserCreatedBy.Admin);
			user.setStudent(student);
		}


		userRepository.save(user);
		return new RegistrationAdminResponseDTO(user.getId().toString());
	}


	@Override
	public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDto) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found"));
		// Update properties from DTO to the entity
		user.setUsername(userRequestDto.getUsername());
		user.setEmail(userRequestDto.getEmail());
		return mapToResponse(userRepository.save(user));
	}

	@Override
	public UserResponseDTO getUserById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found"));
		return mapToResponse(user);
	}

	@Override
	public List<UserResponseDTO> getAllUsers() {
		return userRepository.findAll()
				.stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	private UserResponseDTO mapToResponse(User user) {
		UserResponseDTO dto = new UserResponseDTO();
		// Map properties from entity to DTO
		dto.setId(user.getId());
		dto.setUsername(user.getUsername());
		dto.setEmail(user.getEmail());
		// Add other fields as necessary
		return dto;
	}

	@Transactional
	public String forgotPassword(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
		String otp = otpUtil.generateOtp();

		try {
			emailUtil.sendOtpEmail(email, otp);  // Sends OTP via email
		} catch (MessagingException e) {
			throw new RuntimeException("Unable to send OTP, try again later");
		}

		user.setOtp(otp);
		user.setOtpGeneratedTime(LocalDateTime.now());
		userRepository.save(user);

		return "OTP sent to your email. Please use it to reset your password.";
	}

	@Override
	public String setPassword(String email, String newPassword) {
		return "";
	}

	/**
	 * Verify OTP and set a new password
	 */
	@Transactional
	public String setPassword(String email, String otp, String newPassword) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found with this email: " + email));

		// Validate OTP and expiration (assuming 15 minutes validity)
		if (user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(), LocalDateTime.now()).getSeconds() < (15 * 60)) {
			user.setPassword(passwordEncoder.encode(newPassword));  // Set new password
			user.setOtp(null);  // Clear OTP after successful reset
			userRepository.save(user);
			return "Password reset successful. You can now log in with your new password.";
		}

		return "Invalid OTP or OTP expired. Please regenerate OTP.";
	}

	/**
	 * Regenerate OTP for resetting the password
	 */
	@Transactional
	@Override
	public String regenerateOtp(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
		String otp = otpUtil.generateOtp();

		try {
			emailUtil.sendOtpEmail(email, otp);  // Send new OTP via email
		} catch (MessagingException e) {
			throw new RuntimeException("Unable to send OTP, try again later");
		}

		user.setOtp(otp);
		user.setOtpGeneratedTime(LocalDateTime.now());
		userRepository.save(user);

		return "New OTP has been sent. Please verify your account within 15 minutes.";
	}

	// cloudnary photo uploader:

	// Upload image to Cloudinary

	@Override
	public String uploadProfileImage(Long userId, MultipartFile file) throws IOException {
		// Upload image to Cloudinary
		Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));

		// Extract the URL from the upload result
		String imageUrl = (String) uploadResult.get("secure_url");

		// Find the user and set the image URL
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));
		user.setImage(imageUrl); // Set the Cloudinary image URL in the user model

		// Save the user with the updated image URL
		userRepository.save(user);

		return imageUrl; // Return the image URL (optional)
	}

}