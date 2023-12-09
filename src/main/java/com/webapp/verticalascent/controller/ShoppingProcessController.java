package com.webapp.verticalascent.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapp.verticalascent.dto.ProductDto;
import com.webapp.verticalascent.entity.CartProduct;
import com.webapp.verticalascent.entity.ShoppingSession;
import com.webapp.verticalascent.entity.User;
import com.webapp.verticalascent.service.AddressesService;
import com.webapp.verticalascent.service.CartProductService;
import com.webapp.verticalascent.service.ShoppingSessionService;
import com.webapp.verticalascent.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;
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
	private final AddressesService addressesService;
	
	@Autowired
	public ShoppingProcessController(
		ShoppingSessionService shoppingSessionService,
		CartProductService cartProductService,
		UserService userService,
		AddressesService addressesService
		
	) {
		this.shoppingSessionService = shoppingSessionService;
		this.cartProductService = cartProductService;
		this.userService = userService;
		this.addressesService = addressesService;
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
		List<CartProduct> userCartProducts = new ArrayList<>();
		ShoppingSession shoppingSession = null; // Initialisation de shoppingSession à null
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
		// Case where user is connected
		if (principal instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) principal;
			String userEmail = userDetails.getUsername();
			User user = userService.isEmailExist(userEmail);
			
			if (user != null && shoppingSessionService.getShoppingSessionByUserAndActive(user) != null) {
				// User is connected, we find a shopping session linked to it
				shoppingSession = shoppingSessionService.getShoppingSessionByUserAndActive(user);
			} else if (user != null && shoppingSessionService.isShoppingSessionExistAndShoppingProcessNotEnd(pannierId)) {
				// User is connected, we find a shopping session linked to his localstorage shopping cart
				shoppingSessionService.linkedUserToShoopingSess(user, shoppingSessionService.getShoppingSession(pannierId));
				shoppingSession = shoppingSessionService.getShoppingSessionByUserAndActive(user);
			}
			
		}
		
		// If user is not connect shopping session will be null
		
		if (shoppingSession == null) {
			if (pannierId != null && shoppingSessionService.isShoppingSessionActive(sessionId)) {
				shoppingSession = shoppingSessionService.getShoppingSession(sessionId);
			} else if (shoppingSessionService.isShoppingSessionActive(pannierId)) {
				shoppingSession = shoppingSessionService.getShoppingSession(pannierId);
			} else {
				return "/shopping-cart-empty";
			}
		}
		
		if (shoppingSession != null) {
			userCartProducts.addAll(cartProductService.getCartProductListBySession(shoppingSession));
		}
		
		model.addAttribute("userCartProducts", userCartProducts);
		model.addAttribute("shoppingSession", shoppingSession);
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
	
	/**
	 * Set quantity to 0 for the given CartProduct linked to user session.
	 *
	 * @param request HttpServletRequest
	 * @param productId Get the product id from url
	 * @return /pannier
	 * @throws DataFormatException dataFormatException
	 */
	@GetMapping("/delete-product")
	public String deleteProduct(
		HttpServletRequest request,
		@RequestParam int productId
	) throws DataFormatException {
		HttpSession session = request.getSession();
		String sessionId = session.getId();
		ShoppingSession shoppingSession;
		User user = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// Case where user is connected
		if (principal instanceof UserDetails userDetails) {
			String userEmail = userDetails.getUsername();
			user = userService.isEmailExist(userEmail);
		}
		
		if (shoppingSessionService.isShoppingSessionActive(sessionId)) {
			// Get user active shopping session
			shoppingSession = shoppingSessionService.getShoppingSession(sessionId);
		} else if (!shoppingSessionService.isShoppingSessionActive(sessionId) && user != null) {
			shoppingSession = shoppingSessionService.getShoppingSessionByUserAndActive(user);
		} else {
			return "redirect:/shopping-cart-empty";
		}
		
		// Check if product is link to the current session
		CartProduct cartProductToDelete = cartProductService.getCartProductBySessionAndProductId(shoppingSession, productId);
		if (cartProductToDelete != null) {
			// Update product quantity
			cartProductService.updateCartProductQuantity(cartProductToDelete, 0);
			shoppingSessionService.updateShoppingSessionTotalPrice(shoppingSession, cartProductService.getCartProductListBySession(shoppingSession));
		}
		return "redirect:/pannier";
	}
	
	/**
	 * Empty shopping cart view.
	 *
	 * @return view
	 */
	@GetMapping("/pannier-vide")
	public final String emptyShoppingCart(
		HttpServletRequest request
	
	) {
		// Store user current session
		HttpSession session = request.getSession();
		String sessionId = session.getId();
		return "shopping-cart-empty";
	}
	
	/**
	 * Delivery pages.
	 *
	 * @return view
	 */
	@GetMapping("/livraison")
	public final String delivery(
		Model model
	) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// Case where user is connected
		if (principal instanceof UserDetails userDetails) {
			String userEmail = userDetails.getUsername();
			User user = userService.isEmailExist(userEmail);
			// In case there is no shopping sess link to the current user but still try to access page
			if (shoppingSessionService.getShoppingSessionByUserAndActive(user)  == null) {
				return "shopping-cart-empty";
			}
			if (addressesService.getUserAddresses(user) == null) {
				return "redirect:/address/new-address";
			}
			model.addAttribute("userAddress", addressesService.getUserAddresses(user));
			model.addAttribute(
				"userCartProducts",
				cartProductService.getCartProductListBySession(
					shoppingSessionService.getShoppingSessionByUserAndActive(user))
			);
			model.addAttribute("shoppingSession", shoppingSessionService.getShoppingSessionByUserAndActive(user));
		}
		return "shopping-delivery";
	}
}
