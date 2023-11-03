package com.webapp.verticalascent.controller;

import org.springframework.security.core.Authentication;
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
public class ShoppingProcessController {
	
	@GetMapping("/panier")
	public final String shoppingCart(Authentication authentication) {
		authentication.isAuthenticated();
		return "shopping-cart";
	}
	
	@GetMapping("/panier-vide")
	public final String emptyShoppingCart() {
		return "shopping-cart-empty";
	}
	
	@GetMapping("/livraison")
	public final String delivery() {
		return "shopping-delivery";
	}
}
