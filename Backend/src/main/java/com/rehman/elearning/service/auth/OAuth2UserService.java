package com.rehman.elearning.service.auth;

import java.util.Optional;

import com.rehman.elearning.constants.UserCreatedBy;
import com.rehman.elearning.model.Student;
import com.rehman.elearning.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.rehman.elearning.constants.AuthProviderEnum;
import com.rehman.elearning.constants.ErrorEnum;
import com.rehman.elearning.exceptions.OAuth2AuthenticationProcessingException;
import com.rehman.elearning.model.User;
import com.rehman.elearning.repository.UserRepository;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

	private final UserRepository userRepository;

	@Autowired
	public OAuth2UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

		try {
			return processOAuth2User(oAuth2UserRequest, oAuth2User);
		} catch (AuthenticationException ex) {
			throw ex;
		} catch (Exception ex) {
			// Throwing an instance of AuthenticationException will trigger the
			// OAuth2AuthenticationFailureHandler
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
		}
	}

	private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
				oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
		if (oAuth2UserInfo.getEmail() == null || oAuth2UserInfo.getEmail().isEmpty()) {
			throw new OAuth2AuthenticationProcessingException(ErrorEnum.SSO_ACCOUNT_NOT_FOUND);
		}

		Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
		User user;
		if (userOptional.isPresent()) {
			user = userOptional.get();
			user = updateExistingUser(user, oAuth2UserInfo);
		} else {
			user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
		}

		return User.create(user, oAuth2User.getAttributes());
	}

	private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
		User user = new User();


		user.setAuthProvider(AuthProviderEnum.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
		user.setProviderId(oAuth2UserInfo.getId());
		user.setFullName(oAuth2UserInfo.getName());
		user.setEmail(oAuth2UserInfo.getEmail());
		user.setUsername(oAuth2UserInfo.getEmail().split("@")[0]);
		user.setImage(oAuth2UserInfo.getImageUrl());
		user.setCreatedBy(UserCreatedBy.Self);

		if (user.isTeacher()) {
			Teacher teacher = new Teacher();
			teacher.setCreatedBy(UserCreatedBy.Self);
			teacher.setUser(user); // Set back reference to User
			user.setTeacher(teacher);
		} else {
			Student student = new Student();
			student.setCreatedBy(UserCreatedBy.Self);
			student.setUser(user); // Set back reference to User
			user.setStudent(student);
		}

		return userRepository.save(user);
	}

	private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
		existingUser.setFullName(oAuth2UserInfo.getName());
		existingUser.setImage(oAuth2UserInfo.getImageUrl());
		return userRepository.save(existingUser);
	}

}
