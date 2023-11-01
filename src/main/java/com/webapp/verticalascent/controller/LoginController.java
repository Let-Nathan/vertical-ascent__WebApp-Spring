package com.webapp.verticalascent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@GetMapping("/login/success")
	public final String loginSuccess(@RequestParam String username, @RequestParam String password) {
		
		return "home";
	}
	
}
