package com.webapp.verticalascent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class for Home path.
 *
 * @author Nathan L.
 * @version 1.0
 *
 */
@Controller
public class LoginController {
	
	/**
	 * Login path
	 *
	 * @return The view name
	 *
	 */
	@GetMapping("/login")
	public final String login() {
		return "login";
	}
	
	/**
	 * Register path
	 *
	 * @return The view name
	 *
	 */
	@GetMapping("/register")
	public final String register() {
		return "register";
	}
}
