package com.webapp.verticalascent.controller;

import com.webapp.verticalascent.dto.ProductDto;
import com.webapp.verticalascent.entity.CartProduct;
import com.webapp.verticalascent.entity.Product;
import com.webapp.verticalascent.entity.ShoppingSession;
import com.webapp.verticalascent.service.CartProductService;
import com.webapp.verticalascent.service.ProductService;
import com.webapp.verticalascent.service.ShoppingSessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;



import java.util.*;

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
	private final ProductService productService;
	private final CartProductService cartProductService;
	
	@Autowired
	public ShoppingProcessController(
		ShoppingSessionService shoppingSessionService,
		ProductService productService,
		CartProductService cartProductService
		
	) {
		this.shoppingSessionService = shoppingSessionService;
		this.productService = productService;
		this.cartProductService = cartProductService;
	}
	
	@GetMapping("/pannier")
	public String showShoppingCart(
		HttpServletRequest request,
		@RequestParam String pannierId,
		Model model
	) {
		HttpSession session = request.getSession();
		String sessionId = session.getId();
		System.out.println("Pannier id ==> " + pannierId);
//		cartProductService.validateCartItems()
		
		model.addAttribute("userProduct" );
		return "/shopping-cart";
	}
	
	
	/**
	 * Display shopping cart user if he had one in database.
	 *
	 * @return view
	 */
	@PostMapping("/validate-cart")
	public ResponseEntity<Object> validateCartItems(
		@RequestBody Map<String, Object> requestPayload,
		HttpServletRequest request
	) {
		// Store user current session
		HttpSession session = request.getSession();
		String sessionId = session.getId();
		// Find if there is already a local storage session registered
		String anonymousUserId = (String) requestPayload.get("userId");
		// Get cart product from local storage & convert them to List<ProductDto>
		ObjectMapper objectMapper = new ObjectMapper();
		List<ProductDto> cartItems = objectMapper.convertValue(
			requestPayload.get("cartItems"),
			new TypeReference<>() {
			}
		);
		// Check if product send by user are conventional
		List<ProductDto> validatedItems = cartProductService.validateCartItems(cartItems);
		if (validatedItems == null) {
			return ResponseEntity.notFound().build();
		}
		
		try {
			if (anonymousUserId != null && shoppingSessionService.isShoppingSessionActive(anonymousUserId)) {
				// Anonymous user have an active session & local storage id
				shoppingSessionService.handleExistingShoppingSession(anonymousUserId, validatedItems);
			} else if (anonymousUserId != null && shoppingSessionService.isShoppingSessionExistAndShoppingProcessNotEnd(anonymousUserId)) {
				// Anonymous user have not active Session but have one who exist and shopping process not end.
				// So we activated back the session, and handle cart items with service.
				ShoppingSession shoppingSession = shoppingSessionService.activeShoppingSession(anonymousUserId);
				shoppingSessionService.handleExistingShoppingSession(shoppingSession.getSessionID(), cartItems);
			} else {
				// Anonymous user have no session, so we created one with cart product.
				shoppingSessionService.createNewShoppingSession(sessionId, validatedItems);
			}
		} catch (Exception e) {
			return ResponseEntity.ofNullable(e.getMessage());
		}
		// If user is anonymous we send back in resp session to store it in local storage
		if(anonymousUserId == null) {
			Map<String, Object> response = new HashMap<>();
			response.put("sessionId", sessionId);
			return ResponseEntity.ok().body(response);
		}
		return ResponseEntity.ok("ok");
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
