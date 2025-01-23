package com.rehman.elearning.service.impl;

import com.rehman.elearning.constants.AuthProviderEnum;
import com.rehman.elearning.constants.ErrorEnum;
import com.rehman.elearning.constants.UserCreatedBy;
import com.rehman.elearning.exceptions.*;
import com.rehman.elearning.rest.dto.inbound.LoginRequestDTO;
import com.rehman.elearning.rest.dto.inbound.RegistrationAdminRequestDTO;
import com.rehman.elearning.rest.dto.inbound.RegistrationRequestDTO;
import com.rehman.elearning.rest.dto.inbound.UserRequestDTO;
import com.rehman.elearning.model.Student;
import com.rehman.elearning.model.Teacher;
import com.rehman.elearning.model.User;
import com.rehman.elearning.repository.UserRepository;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
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


	@Value("${profile.images.server}")
	private String mediaServerUrl;

	@Value("${profile.images.upload}")
	private String mediaUploadDir;

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
		return new LoginResponseDTO(user.getId(), token, user.getUsername(),user.isTeacher(),user.isStudent(),user.getAdmin());
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
		user.setAdmin(false);
//		user.setAdmin(Boolean.TRUE.equals(registrationRequestDTO.getAdmin())); // uncommit this and  registrationDTO when you want to create an admin
		user.setImage("https://vectorified.com/no-profile-picture-icon#no-profile-picture-icon-28.png"); // Default image URL
		user.setAuthProvider(AuthProviderEnum.TraditionalLogin); // Assuming traditional login for registration
		user.setProviderId(UUID.randomUUID().toString()); // Generate a unique provider ID
		user.setAsTeacher(registrationRequestDTO.getIsTeacher());



		// Role assignment based on registration request
//		if (registrationRequestDTO.getIsTeacher()) {
//			Teacher teacher = new Teacher(this);
//			teacher.setUser(user);
//			user.setTeacher(teacher);
//			teacher.setCreatedBy(UserCreatedBy.Self);
//		} else {
//			Student student = new Student();
//			student.setUser(user);
//			user.setStudent(student);
//			student.setCreatedBy(UserCreatedBy.Self);
//		}

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
		user.setImage("https://vectorified.com/no-profile-picture-icon#no-profile-picture-icon-28.png"); // Default image URL
		user.setAuthProvider(AuthProviderEnum.TraditionalLogin);
		user.setProviderId(UUID.randomUUID().toString());
		user.setCreatedBy(UserCreatedBy.Admin); // Set createdBy to "Admin"
		user.setAsTeacher(registrationAdminRequestDTO.getIsTeacher());

		// Role assignment based on registration request
//		if (registrationAdminRequestDTO.getIsTeacher()) {
//			Teacher teacher = new Teacher(this);
//			teacher.setUser(user);
//			teacher.setCreatedBy(UserCreatedBy.Admin);
//			user.setTeacher(teacher);
//		} else {
//			Student student = new Student();
//			student.setUser(user);
//			student.setCreatedBy(UserCreatedBy.Admin);
//			user.setStudent(student);
//		}


		userRepository.save(user);
		return new RegistrationAdminResponseDTO(user.getId().toString());
	}

	@Override
	@Transactional
	public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDto) throws IOException {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found"));

		String base64Image = userRequestDto.getProfilePicture();
		if((base64Image!=null || base64Image.length()>0) && !base64Image.equals("default-profile.png")) {
			// Remove the base64 prefix if it exists (e.g., "data:image/jpeg;base64,")
			if (base64Image.startsWith("data:image")) {
				base64Image = base64Image.split(",")[1]; // Extract the actual base64 encoded string
			}
			byte[] imageBytes = Base64.getDecoder().decode(base64Image);
			// Save the image to a local directory or to a cloud service
			String imagePath = saveImage(imageBytes, user.getId());
			user.setImage(imagePath);
		}
		// Update properties from DTO to the entity
		user.setFullName(userRequestDto.getFullName());
		user.setEmail(userRequestDto.getEmail());
		return mapToResponse(userRepository.save(user));
	}

	@Override
	@Transactional
	public UserResponseDTO updateUserByAdmin(Long userId, UserRequestDTO userRequestDto) throws IOException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));

		user.setUsername(userRequestDto.getFullName());
		user.setFullName(userRequestDto.getFullName());
		user.setEmail(userRequestDto.getEmail());
		user.setPassword(userRequestDto.getPassword());


		// Handle isTeacher field
		if (userRequestDto.getIsTeacher() != null && userRequestDto.getIsTeacher()) {
			// If the user is marked as a teacher, ensure a Teacher entity is associated
			if (user.getTeacher() == null) {
				Teacher teacher = new Teacher();
				teacher.setUser(user);
				teacher.setCreatedBy(UserCreatedBy.Admin);
				user.setTeacher(teacher);
			}
			user.setStudent(null); // Ensure the user is not a student
		} else {
			// If the user is not marked as a teacher, ensure a Student entity is associated
			if (user.getStudent() == null) {
				Student student = new Student();
				student.setUser(user);
				student.setCreatedBy(UserCreatedBy.Admin);
				user.setStudent(student);
			}
			user.setTeacher(null); // Ensure the user is not a teacher
		}

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
		dto.setusername(user.getUsername());
		dto.setEmail(user.getEmail());
		dto.setTeacehr(user.isTeacher());
		dto.setImage(user.getImage());
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
	// code of seperation start
	// Separate OTP validation method
	@Override
	public boolean validateOtp(String email, String otp) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found with this email: " + email));

		// Validate OTP and expiration (assuming 15 minutes validity)
		if (user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(), LocalDateTime.now()).getSeconds() < (15 * 60)) {
			return true;  // OTP is valid
		}
		return false;  // OTP is invalid or expired
	}

	// Method to set the password after OTP validation
	@Override
	@Transactional
	public String setPassword(String email, String otp, String newPassword) {
		// First validate the OTP
		if (!validateOtp(email, otp)) {
			return "Invalid OTP or OTP expired. Please regenerate OTP.";
		}

		// OTP is valid, now reset the password
		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found with this email: " + email));

		user.setPassword(passwordEncoder.encode(newPassword));  // Set new password
		user.setOtp(null);  // Clear OTP after successful reset
		userRepository.save(user);

		return "Password reset successful. You can now log in with your new password.";
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

	private String saveImage(byte[] imageBytes, Long userId) throws IOException {
		// Save the image to a local directory or use cloud service (e.g., Cloudinary)
		String fileName = UUID.randomUUID().toString() + ".jpg";
		File directory = new File(mediaUploadDir);
		if (!directory.exists()) {
			directory.mkdirs();  // Create the directory if it doesn't exist
		}

		String filePath = mediaUploadDir + fileName;
		// Create the directory if it doesn't exist

		// Example saving to local file system (adjust for your needs)
		try (FileOutputStream fos = new FileOutputStream(new File(filePath))) {
			fos.write(imageBytes);
		}
		return mediaServerUrl+fileName; // Return the image path or URL to store in the database
	}
}