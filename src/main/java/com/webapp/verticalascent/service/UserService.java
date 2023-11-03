package com.webapp.verticalascent.service;

import com.webapp.verticalascent.entity.Role;
import com.webapp.verticalascent.entity.User;
import com.webapp.verticalascent.repository.UserRepository;
import java.sql.Timestamp;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
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
	private final RoleService roleService;
	
	@Autowired
	public UserService(
		UserRepository userRepository,
		RoleService roleService
	) {
		this.userRepository = userRepository;
		this.roleService = roleService;
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
		// Vérifiez si l'utilisateur a déjà un rôle
		if (user.getRole() == null) {
			// Si l'utilisateur n'a pas de rôle, attribuez-lui le rôle par défaut
			Role defaultRole = roleService.getDefaultRole();
			user.setRole(defaultRole);
		}
		userRepository.save(user);
	}
	
	/**
	 * Return true if method find the email addresses in database with using
	 * UserRepository dependency injection.
	 *
	 * @param email string e-mail addresses.
	 * @return boolean
	 */
	public User isEmailExist(String email) {
		return userRepository.findByEmail(email);
	}
	
}
