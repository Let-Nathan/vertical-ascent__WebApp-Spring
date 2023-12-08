package com.webapp.verticalascent.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapp.verticalascent.dto.ProductDto;
import com.webapp.verticalascent.entity.CartProduct;
import com.webapp.verticalascent.entity.ShoppingSession;
import com.webapp.verticalascent.entity.User;
import com.webapp.verticalascent.service.CartProductService;
import com.webapp.verticalascent.service.ShoppingSessionService;
import com.webapp.verticalascent.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;




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
	private final CartProductService cartProductService;
	private final UserService userService;
	
	@Autowired
	public ShoppingProcessController(
		ShoppingSessionService shoppingSessionService,
		CartProductService cartProductService,
		UserService userService
		
	) {
		this.shoppingSessionService = shoppingSessionService;
		this.cartProductService = cartProductService;
		this.userService = userService;
	}
	
	
	/**
	 * Return view for user shopping cart.
	 *
	 * @param request HttpServletRequest get the current session id
	 * @param pannierId String representation of anonymous user pannier id (sess id)
	 * @param model Model add attributes to the thymeleaf view.
	 * @return view
	 */
	@GetMapping("/pannier")
	public String showShoppingCart(
		HttpServletRequest request,
		@RequestParam(required = false) String pannierId,
		Model model
	) {
		HttpSession session = request.getSession();
		String sessionId = session.getId();
		List<CartProduct> userCartProduct = new ArrayList<>();
		ShoppingSession shoppingSession; // Init shoppingSession to null
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		// Check if the user is connected and has an active session
		if (principal instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) principal;
			String userEmail = userDetails.getUsername();
			User user = userService.isEmailExist(userEmail);
			
			if (user != null) {
				// If the user is connected, look for an active session
				shoppingSession = shoppingSessionService.getShoppingSessionByUserAndActive(user);
				if (shoppingSession != null) {
					userCartProduct.addAll(cartProductService.getCartProductListBySession(shoppingSession));
				}
			} else {
				// If the user is not connected, check for a session related to pannierId
				if (pannierId != null && shoppingSessionService.isShoppingSessionActive(pannierId)) {
					shoppingSession = shoppingSessionService.getShoppingSession(pannierId);
					userCartProduct.addAll(cartProductService.getCartProductListBySession(shoppingSession));
				} else {
					return "/shopping-cart-empty";
				}
			}
		} else {
			// If the user is not connected, check for a session related to pannierId
			if (pannierId != null && shoppingSessionService.isShoppingSessionActive(pannierId)) {
				shoppingSession = shoppingSessionService.getShoppingSession(pannierId);
				userCartProduct.addAll(cartProductService.getCartProductListBySession(shoppingSession));
			} else {
				return "/shopping-cart-empty";
			}
		}
		
		model.addAttribute("userProduct", userCartProduct);
		model.addAttribute("shoppingSession", shoppingSession); // Add ShoppingSession to model
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
		List<ProductDto> cartItems = objectMapper.convertValue(requestPayload.get("cartItems"), new TypeReference<>() {});
		// Check if product send by user are conventional
		List<ProductDto> validatedItems = cartProductService.validateCartItems(cartItems);
		
		Map<String, Object> response = new HashMap<>();
		
		if (validatedItems == null) {
			return ResponseEntity.notFound().build();
		}
		
		try {
			if (
				anonymousUserId != null
				&& shoppingSessionService.isShoppingSessionActive(anonymousUserId)
			) {
				// Anonymous user have an active session & local storage id
				shoppingSessionService.handleExistingShoppingSession(
					anonymousUserId,
					validatedItems
				);
			} else if (
				anonymousUserId != null
				&& shoppingSessionService
					.isShoppingSessionExistAndShoppingProcessNotEnd(anonymousUserId)
			) {
				// Anonymous user have not active Session but have one who exist and shopping process not end.
				// So we activated back the session, and handle cart items with service.
				ShoppingSession shoppingSession = shoppingSessionService.activeShoppingSession(anonymousUserId);
				shoppingSessionService.handleExistingShoppingSession(shoppingSession.getSessionId(), cartItems);
			} else {
				// Anonymous user have no session, so we created one with cart product.
				shoppingSessionService.createNewShoppingSession(sessionId, validatedItems);
			}
		} catch (Exception e) {
			return ResponseEntity.ofNullable(e.getMessage());
		}
		// If user is anonymous we send back in resp session to store it in local storage
		if (anonymousUserId == null) {
			response.put("sessionId", sessionId);
			return ResponseEntity.ok().body(response);
		}
		response.put("sessionId", anonymousUserId);
		return  ResponseEntity.ok().body(response);
	}
	
//	@GetMapping("/delete-product")
//	public String deleteProduct(
//		HttpServletRequest request,
//		@RequestParam String productId,
//		Model model
//	) {
//		HttpSession session = request.getSession();
//		String sessionId = session.getId();
//
//
//	}
	
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
