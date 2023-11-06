package com.webapp.verticalascent.controller;

import com.webapp.verticalascent.dto.UserRegistrationDto;
import com.webapp.verticalascent.entity.User;
import com.webapp.verticalascent.service.DtoToEntityConversionService;
import com.webapp.verticalascent.service.ErrorsLogService;
import com.webapp.verticalascent.service.UserService;
import jakarta.validation.Valid;
import org.hibernate.exception.DataException;
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
	
	private final DtoToEntityConversionService dtoToEntityConversionService;
	private final ErrorsLogService errorsLogService;
	private final UserService userService;
	
	/**
	 * Dependency injection for userRegistrationService.
	 *
	 * @param dtoToEntityConversionService (UserRegistrationService)
	 */
	@Autowired
	public RegistrationController(
		final DtoToEntityConversionService dtoToEntityConversionService,
		final ErrorsLogService errorsLogService,
		final UserService userService
		) {
		this.dtoToEntityConversionService = dtoToEntityConversionService;
		this.errorsLogService = errorsLogService;
		this.userService = userService;
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
		model.addAttribute("userRegistrationDto", userRegistrationDto);
		return "register";
	}
	
	/**
	 * The input form is validated by UserRegistrationDto with @valid annotation.
	 * If errors happens, BindingResult store it and we can tell to the end user,
	 * what happens.
	 * UserService is used to check if there is no similar e-mail, already store.
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
		//Check errors from BindingResult comparison with UserRegistrationDto, return error true.
		if (result.hasErrors() || userService.isEmailExist(userRegistrationDto.getEmail()) != null) {
			result.rejectValue(
				"email",
				"userRegistrationDto",
				"L'adresse e-mail est déjà utilisé."
			);
			return "register";
		} else {
			//Trying to insert the user into the database.
			try {
				//Convert UserDto into User object before insertion into database.
				User user = dtoToEntityConversionService.convertUserRegistrationDtoToEntity(userRegistrationDto);
				//Store User into database.
				userService.registerUser(user);
			} catch (DataException dataEx) {
				//Saved potential errors into errors log entity
				errorsLogService.storeLogs(dataEx);
			}
			return "redirect:/login";
		}
	}
}
