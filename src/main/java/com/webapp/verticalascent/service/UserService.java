package com.webapp.verticalascent.service;

import com.webapp.verticalascent.entity.User;
import com.webapp.verticalascent.repository.UserRepository;
import java.sql.Timestamp;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
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
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	/**
	 * Take User object as parameter and saved it into database.
	 *
	 * @param user User object representation
	 */
	public void registerUser(User user) {
		user.setInscriptionDate(Timestamp.from(Instant.now()));
		userRepository.save(user);
	}
	
	/**
	 * Find a potential user email in use
	 *
	 * @param email string e-mail addresses.
	 * @return boolean
	 */
	public boolean userEmailExist(String email) {
		return userRepository.findByEmailExists(email) != null;
	}
}
