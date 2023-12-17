package com.webapp.verticalascent.controller;

import com.webapp.verticalascent.entity.User;
import com.webapp.verticalascent.service.AddressesService;
import com.webapp.verticalascent.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class for User.
 *
 * @author Nathan L.
 * @version 1.0
 *
 */
@Controller
public class UserController {
	
	private final AddressesService addressesService;
	private final UserService userService;
	
	
	@Autowired
	public UserController (
		AddressesService addressesService,
		UserService userService
		) {
		this.addressesService = addressesService;
		this.userService = userService;
	}
	
	/**
	 * Display user's account information.
	 *
	 * @return view
	 */
	@GetMapping("/account")
	public final String login(
		Model model
	) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			Object principal = authentication.getPrincipal();
			
			if (principal instanceof UserDetails) {
				UserDetails userDetails = (UserDetails) principal;
				String userEmail = userDetails.getUsername();
				User user = userService.isEmailExist(userEmail);
				if(addressesService.getUserAddresses(user) != null ) {
					model.addAttribute("userAddress", addressesService.getUserAddresses(user));
				}
				model.addAttribute("user", user);
			}
		}
			return "user-account";
	}
	
}
