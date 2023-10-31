package com.webapp.verticalascent.controller;

import com.webapp.verticalascent.dto.UserRegistrationDto;
import com.webapp.verticalascent.entity.User;
import com.webapp.verticalascent.service.UserRegistrationService;
import com.webapp.verticalascent.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	 * Dependency injection for userRegistrationService.
	 *
	 * @param userRegistrationService (UserRegistrationService)
	 */
	@Autowired
	public RegistrationController(
		final UserRegistrationService userRegistrationService,
		final UserService userService
	) {
		this.userRegistrationService = userRegistrationService;
		this.userService = userService;
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
		model.addAttribute("userRegistrationDTO", userRegistrationDto);
		return "register";
	}
	
	/**
	 * Saved form if is validated by associated dto.
	 *
	 * @param userRegistrationDto (Data transfer object)
	 * @param result (Errors from validation form)
	 * @return redirect (Home page redirect after validation successful)
	 */
	@RequestMapping(value = "/register/save")
	public String registerSaved(
		@Valid UserRegistrationDto userRegistrationDto,
		BindingResult result
	) {
		if (result.hasErrors()) {
			return "register";
		}
		
		User user = userRegistrationService.convertUserRegistrationDtoToEntity(
			userRegistrationDto
		);
		
		userService.registerUser(user);
		
		return "redirect:/";
	}
}
