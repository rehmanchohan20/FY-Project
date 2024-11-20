package com.rehman.elearning.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rehman.elearning.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Query("select u from User u where u.username= :username or u.email =:username")
	Optional<User> findByUsernameOrEmail(String username);

	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
}
