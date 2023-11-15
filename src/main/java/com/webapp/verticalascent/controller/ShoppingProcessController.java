package com.webapp.verticalascent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
	 * @return view
	 */
	@GetMapping("/pannier")
	public final String shoppingCart() {
		
		return "/shopping-cart-empty";
	}
	
	
	/**
	 * Empty shopping cart view.
	 *
	 * @return view
	 */
	@GetMapping("/pannier-vide")
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
