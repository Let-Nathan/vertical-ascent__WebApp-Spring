package com.webapp.verticalascent.config;

import com.webapp.verticalascent.entity.User;
import com.webapp.verticalascent.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
public class CustomAuthenticationManager implements AuthenticationManager {
	
	private final UserService userService;
	
	public CustomAuthenticationManager(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) {
		
		String email = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		// Utilisez le service utilisateur pour rechercher l'utilisateur en fonction de l'adresse e-mail
		User user = userService.isEmailExist(email);
		
		if (user != null && isPasswordValid(user, password)) {
			List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
			return new UsernamePasswordAuthenticationToken(email, password, authorities);
		} else {
			throw new BadCredentialsException("Informations d'identification incorrectes");
		}
	}
	
	private boolean isPasswordValid(User user, String password) {
		return BCrypt.checkpw(password, user.getPassword());
	}
}
