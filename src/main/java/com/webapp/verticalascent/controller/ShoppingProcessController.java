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
public class ShoppingProcessController {
	
	@GetMapping("/panier")
	public final String shoppingCart() {
		return "shopping-cart";
	}
	
	@GetMapping("/panier-vide")
	public final String emptyShoppingCart() {
		return "empty-shopping-cart";
	}
	
	@GetMapping("/livraison")
	public final String delivery() {
		return "delivery";
	}
}
