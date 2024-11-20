package com.rehman.elearning.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rehman.elearning.model.User;
import com.rehman.elearning.repository.UserRepository;

@Service
public class AuthServicImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsernameOrEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User name not found: " + username));
		return user;
	}
}
