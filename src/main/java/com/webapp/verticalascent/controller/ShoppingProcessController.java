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
	
	/**
	 * Display shopping cart user if he had one in database.
	 *
	 * @param authentication verify that user is connected
	 * @return view
	 */
	@GetMapping("/panier")
	public final String shoppingCart(Authentication authentication) {
		if(authentication.isAuthenticated()){
			return "shopping-cart";
		}
		return "/shopping-cart-empty";
	}
	
	/**
	 * Empty shopping cart view.
	 *
	 * @return view
	 */
	@GetMapping("/panier-vide")
	public final String emptyShoppingCart() {
		return "shopping-cart-empty";
	}
	
	/**
	 * Delivery pages.
	 *
	 * @return view
	 */
	@GetMapping("/livraison")
	public final String delivery() {
		return "shopping-delivery";
	}
}
