package com.rehman.elearning.service.auth;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.rehman.elearning.constants.RoleEnum;
import com.rehman.elearning.constants.UserCreatedBy;
import com.rehman.elearning.exceptions.AccountAlreadyRegisterException;
import com.rehman.elearning.exceptions.AccountNotRegisterException;
import com.rehman.elearning.model.Student;
import com.rehman.elearning.model.Teacher;
import com.rehman.elearning.util.AuthorityUtils;
import com.rehman.elearning.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.rehman.elearning.constants.AuthProviderEnum;
import com.rehman.elearning.constants.ErrorEnum;
import com.rehman.elearning.exceptions.OAuth2AuthenticationProcessingException;
import com.rehman.elearning.model.User;
import com.rehman.elearning.repository.UserRepository;

import javax.management.relation.Role;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

	private final UserRepository userRepository;
	private final HttpServletRequest request;
	public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
	@Autowired
	public OAuth2UserService(UserRepository userRepository, HttpServletRequest request) {
		super();
		this.userRepository = userRepository;
		this.request=request;
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
		String profession = getQueryParameterValue("profession");
		if (userOptional.isPresent() && (profession==null || profession.isEmpty())) {
			user = userOptional.get();
			user = updateExistingUser(user, oAuth2UserInfo);
			return User.create(user, oAuth2User.getAttributes());
		} else if(!userOptional.isPresent() &&  (profession!=null && !profession.isEmpty())) {
			RoleEnum role = RoleEnum.valueOf(profession);
			user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo, role);
			return User.create(user, oAuth2User.getAttributes());
		}

		if(userOptional.isPresent()){
			throw new AccountAlreadyRegisterException(ErrorEnum.ACCOUNT_ALREADY_EXIST);
		}
		throw new AccountNotRegisterException(ErrorEnum.ACCOUNT_NOT_REGISTER);
	}


	private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo, RoleEnum roleEnum) {
		User user = new User();
		user.setAuthProvider(AuthProviderEnum.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
		user.setProviderId(oAuth2UserInfo.getId());
		user.setFullName(oAuth2UserInfo.getName());
		user.setEmail(oAuth2UserInfo.getEmail());
		user.setUsername(oAuth2UserInfo.getEmail().split("@")[0]);
		user.setImage(oAuth2UserInfo.getImageUrl());
		user.setCreatedBy(UserCreatedBy.Self);
		if (RoleEnum.TEACHER.equals(roleEnum)) {
			Teacher teacher = new Teacher();
			teacher.setCreatedBy(UserCreatedBy.Self);
			teacher.setUser(user); // Set back reference to User
			user.setTeacher(teacher);
		} else if(RoleEnum.STUDENT.equals(roleEnum)) {
			Student student = new Student();
			student.setCreatedBy(UserCreatedBy.Self);
			student.setUser(user); // Set back reference to User
			user.setStudent(student);
		}
		return userRepository.saveAndFlush(user);
	}

	private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
		existingUser.setFullName(oAuth2UserInfo.getName());
		existingUser.setImage(oAuth2UserInfo.getImageUrl());
		return userRepository.saveAndFlush(existingUser);
	}

	private String getQueryParameterValue(String key){
		Optional<Cookie> cookie = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME);
		Map<String, String> params =  CookieUtil.getQueryParams(cookie.get().getValue());
		String value = params.get(key);
		return value;
	}
}
