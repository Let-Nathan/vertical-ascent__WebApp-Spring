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
	
	/**
	 * Display user's account information.
	 *
	 * @return view
	 */
	@GetMapping("/account")
	public final String login() {
		return "user-account";
	}
	
	/**
	 * Access to new-addresses pages @todo impl multiple addresses.
	 *
	 * @return view
	 */
	@GetMapping("/new-addresses")
	public final String newAddresses() {
		return "user-address";
	}
}
