package com.rehman.elearning.model;

import com.rehman.elearning.constants.AuthProviderEnum;
import com.rehman.elearning.constants.RoleEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@Entity
@Table(name = "user")
@DynamicUpdate
public class User extends CommonEntity implements OAuth2User, UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "username", unique = true)
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "image")
	private String image;

	@Column(name = "IS_ADMIN")
	private Boolean isAdmin = false;

	@Column(name = "OTP")
	private String otp;

	@Column(name = "otp_generated_time")
	private LocalDateTime otpGeneratedTime;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@PrimaryKeyJoinColumn
	private Student student;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@PrimaryKeyJoinColumn
	private Teacher teacher;

	@Enumerated(EnumType.STRING)
	@Column(name = "provider")
	private AuthProviderEnum authProvider;

	@Column(name = "providerId")
	private String providerId;


	public LocalDateTime getOtpGeneratedTime() {
		return otpGeneratedTime;
	}

	public void setOtpGeneratedTime(LocalDateTime otpGeneratedTime) {
		this.otpGeneratedTime = otpGeneratedTime;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	@Transient
	private Map<String, Object> attributes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Boolean getAdmin() {
		return isAdmin;
	}

	public void setAdmin(Boolean admin) {
		isAdmin = admin;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public AuthProviderEnum getAuthProvider() {
		return authProvider;
	}

	public void setAuthProvider(AuthProviderEnum authProvider) {
		this.authProvider = authProvider;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public boolean isTeacher() {
		return teacher != null;
	}

	public boolean isStudent() {
		return student != null;
	}


	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String role = "ROLE_";
		if (Boolean.TRUE.equals(isTeacher())) {
			return Arrays.asList(new SimpleGrantedAuthority(role + RoleEnum.TEACHER));
		} else if (Boolean.TRUE.equals(isStudent())) {
			return Arrays.asList(new SimpleGrantedAuthority(role + RoleEnum.STUDENT));
		} else if(Boolean.TRUE.equals(getAdmin())) {
			return Arrays.asList(new SimpleGrantedAuthority(role + RoleEnum.ADMIN));
		}
		return Arrays.asList(new SimpleGrantedAuthority(role + RoleEnum.GUEST));
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String getName() {
		return getFullName();
	}

	public User() {

	}

	public User(Long id, String username, String email, String password) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.username = username;
	}

	public static User create(User user) {
		return new User(user.getId(), user.getEmail().split("@")[0], user.getEmail(), user.getPassword());
	}

	public static User create(User user, Map<String, Object> attributes) {
		User userPrincipal = User.create(user);
		userPrincipal.setAttributes(attributes);
		return userPrincipal;
	}
}
