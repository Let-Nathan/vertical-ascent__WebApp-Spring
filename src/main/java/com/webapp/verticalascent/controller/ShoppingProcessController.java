package com.webapp.verticalascent.controller;

import com.webapp.verticalascent.dto.ProductDto;
import com.webapp.verticalascent.entity.CartProduct;
import com.webapp.verticalascent.entity.Product;
import com.webapp.verticalascent.entity.ShoppingSession;
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
	
	@Autowired
	public ShoppingProcessController(
		ShoppingSessionService shoppingSessionService,
		ProductService productService
		
	) {
		this.shoppingSessionService = shoppingSessionService;
		this.productService = productService;
	}
	
	@GetMapping("/pannier")
	public String showShoppingCart(
		HttpServletRequest request,
		Model model
	) {
		HttpSession session = request.getSession();
		String sessionId = session.getId();
		
		// Récupérer la ShoppingSession en fonction du sessionId
		ShoppingSession userShoppingSession = shoppingSessionService.isShoppingSessionActive(sessionId);
		
		if (userShoppingSession != null) {
			// Récupérer les CartProducts associés à la ShoppingSession
			List<CartProduct> cartProducts = userShoppingSession.getCartProducts();
			
			List<Optional<Product>> products = new ArrayList<>();
			
			for (CartProduct cartProduct : cartProducts) {
				Optional<Product> product = productService.findOneById(cartProduct.getProduct().getId());
				products.add(product);
			}
			model.addAttribute("products", products);
		}
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
		HttpSession session = request.getSession();
		String sessionId = session.getId();
		String userId = (String) requestPayload.get("userId");
		
		ObjectMapper objectMapper = new ObjectMapper();
		List<ProductDto> cartItems = objectMapper.convertValue(
			requestPayload.get("cartItems"),
			new TypeReference<>() {
			}
		);
		
		List<ProductDto> validatedItems = shoppingSessionService.validateCartItems(cartItems);
		if (validatedItems == null) {
			return ResponseEntity.notFound().build();
		}
		Map<String, Object> response = new HashMap<>();
		response.put("sessionId", sessionId);
		if(userId != null) {
			shoppingSessionService.userShoppingSession(userId, validatedItems);
		} else {
			shoppingSessionService.userShoppingSession(sessionId, validatedItems);
		}
		
		return ResponseEntity.ok().body(response);
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
