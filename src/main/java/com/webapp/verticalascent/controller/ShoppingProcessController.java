package com.webapp.verticalascent.controller;

import com.webapp.verticalascent.dto.ProductDto;
import com.webapp.verticalascent.service.ProductCategoryService;
import com.webapp.verticalascent.service.ProductService;
import com.webapp.verticalascent.service.ShoppingSessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Soundbank;
import java.util.List;

/**
 * Controller class for user purchasing process.
 *
 * @author Nathan L.
 * @version 1.0
 *
 */
@Controller
public class ShoppingProcessController {
	
	private final ShoppingSessionService shoppingSessionService;
	
	@Autowired
	public ShoppingProcessController(
		ShoppingSessionService shoppingSessionService
	) {
		this.shoppingSessionService = shoppingSessionService;
	}
	
	@GetMapping("/pannier")
	public String showShoppingCart() {
		// Redirection vers la page du panier
		return "/shopping-cart";
	}
	
	/**
	 * Display shopping cart user if he had one in database.
	 *
	 * @return view
	 */
	
	@PostMapping("/validate-cart")
	public String validateCartItems(
		@Valid @RequestBody List<ProductDto> cartItems,
		BindingResult result,
		HttpServletRequest request
	) {
		
		if(result.hasErrors()) {
			String referer = request.getHeader("Referer");
			return "redirect:" + referer ;
		}
		
		List<ProductDto> validatedItems = shoppingSessionService.validateCartItems(cartItems);
		// Vérification des éléments du panier et renvoi de la liste validée
		return "/livraison";
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
