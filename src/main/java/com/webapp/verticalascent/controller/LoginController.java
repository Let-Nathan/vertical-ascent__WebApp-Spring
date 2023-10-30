package com.webapp.verticalascent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class for Login.
 *
 * @author Nathan L.
 * @version 1.0
 *
 */
@Controller
public class LoginController {
	
	@GetMapping("/login")
	public final String login() {
		return "login";
	}
	
}
