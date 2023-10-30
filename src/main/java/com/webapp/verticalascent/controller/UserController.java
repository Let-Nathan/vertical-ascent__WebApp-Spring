package com.webapp.verticalascent.controller;

import org.springframework.stereotype.Controller;
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
	
	@GetMapping("/account")
	public final String login() {
		return "user-account";
	}
	
	@GetMapping("/new-addresses")
	public final String newAddresses() {
		return "user-addresses";
	}
}
