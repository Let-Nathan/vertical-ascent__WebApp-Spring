package com.webapp.verticalascent.service;

import com.webapp.verticalascent.entity.User;
import com.webapp.verticalascent.repository.UserRepository;
import java.sql.Timestamp;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service implementation with logic for User.
 *
 * @author Nathan L.
 * @version 1.0
 */
@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	@Autowired
	public UserService(
		UserRepository userRepository
	) {
		this.userRepository = userRepository;
	}
	
	/**
	 * Take User object as parameter and saved it into database.
	 * We also provide a creation timestamp and password encryption with BCrypt Beans.
	 *
	 * @param user User object representation
	 */
	public void registerUser(User user) {
		user.setInscriptionDate(Timestamp.from(Instant.now()));
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	
	/**
	 * Find a potential user email in use.
	 *
	 * @param email string e-mail addresses.
	 * @return boolean
	 */
	public User isEmailExist(String email) {
		return userRepository.findByEmail(email);
	}
	
}
