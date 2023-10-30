package com.webapp.verticalascent.controller;

import com.webapp.verticalascent.dto.UserRegistrationDTO;
import com.webapp.verticalascent.entity.User;
import com.webapp.verticalascent.service.UserRegistrationService;
import com.webapp.verticalascent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller for Registration.
 *
 * @author Nathan L.
 * @version 1.0
 *
 */
@Controller
public class RegistrationController {
	
	private final UserRegistrationService userRegistrationService;
	private final UserService userService;
	/**
	 * Dependency injection for userRegistrationService
	 *
	 * @param userRegistrationService (UserRegistrationService)
	 */
	@Autowired
	public RegistrationController (
		final UserRegistrationService userRegistrationService,
		final UserService userService
	)
	{
		this.userRegistrationService = userRegistrationService;
		this.userService = userService;
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@PostMapping("/register")
	public String registerUser (@ModelAttribute("userRegistrationDTO") UserRegistrationDTO userRegistrationDTO, BindingResult result) {
		if (result.hasErrors()) {
			return "register";
		}
		
		User user = userRegistrationService.convertUserRegistrationDTOToEntity(userRegistrationDTO);
		
		userService.registerUser(user);
		
		return "redirect:/login";
	}
}
