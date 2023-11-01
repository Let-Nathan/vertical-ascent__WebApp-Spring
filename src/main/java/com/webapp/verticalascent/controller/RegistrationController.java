package com.webapp.verticalascent.controller;

import com.webapp.verticalascent.dto.UserRegistrationDto;
import com.webapp.verticalascent.entity.ErrorsLog;
import com.webapp.verticalascent.entity.User;
import com.webapp.verticalascent.service.DtoToEntityConversionService;
import com.webapp.verticalascent.service.ErrorsLogService;
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
	 * Saved form if is valid by associated dto.
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
		//Check errors from UserReg Dto, return errors to view if true.
		if (result.hasErrors()) {
			return "register";
		}
		
		//We assume that form is valid after dto validation.
		User user = dtoToEntityConversionService.convertUserRegistrationDtoToEntity(
			userRegistrationDto
		);
		
		//Check if there is no user email
		if (userService.userEmailExist(user.getEmail())) {
			result.rejectValue(
				"email",
				"userRegistrationDto",
				"E-mail addresses already in use."
			);
			return "register";
		} else {
			try {
				userService.registerUser(user);
			} catch (Exception e) {
				// saved potential errors into errors log entity
				errorsLogService.errorLogTraitment(e);
			}
			return "redirect:/";
		}
	}
}
