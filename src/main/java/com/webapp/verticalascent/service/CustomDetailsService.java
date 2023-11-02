package com.webapp.verticalascent.service;

import com.webapp.verticalascent.entity.Role;
import com.webapp.verticalascent.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Service implementation with logic for User.
 *
 * @author Nathan L.
 * @version 1.0
 */
@Service
public class CustomDetailsService implements UserDetailsService {
	
	private final UserService userService;
	
	@Autowired
	public CustomDetailsService(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * @param email the email of the user to load
	 * @return userDetails UserDetails object representing the user
	 * @throws UsernameNotFoundException if the user is not found
	 *                                   <p>
	 *                                   The method to load a user by their email.
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userService.isEmailExist(email);
		
		if (user != null) {
			return new org.springframework.security.core.userdetails.User(
				user.getEmail(),
				user.getPassword(),
				mapRolesToAuthorities(user.getRole())
				
				);
		} else {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
	}
	
	/**
	 * The method to map the user's role to a collection of GrantedAuthority objects.
	 *
	 * @param role the user's role.
	 * @return a collection of GrantedAuthority object.
	 *
	 */
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Role role) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role.getName()));
		return authorities;
	}
}
