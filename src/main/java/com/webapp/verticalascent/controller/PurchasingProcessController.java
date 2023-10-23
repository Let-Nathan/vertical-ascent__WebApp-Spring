package com.webapp.verticalascent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class for user purchasing process.
 *
 * @author Nathan L.
 * @version 1.0
 *
 */
@Controller
public class PurchasingProcessController {
	
	@GetMapping("/shopping_cart")
	public final String shoppingCart() {
		return "shopping_cart";
	}
}
