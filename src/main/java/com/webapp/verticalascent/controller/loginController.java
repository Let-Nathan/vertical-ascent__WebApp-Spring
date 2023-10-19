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
public class loginController {
	
	/**
	 * Default home path ==> @Todo Router config file.
	 *
	 * @return The view name
	 *
	 */
	@GetMapping("/login")
	public final String home() {
		return "login";
	}
}
