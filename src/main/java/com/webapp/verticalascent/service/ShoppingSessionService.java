package com.webapp.verticalascent.service;

import com.webapp.verticalascent.dto.ProductDto;
import com.webapp.verticalascent.entity.CartProduct;
import com.webapp.verticalascent.entity.Product;
import com.webapp.verticalascent.entity.ShoppingSession;
import com.webapp.verticalascent.entity.User;
import com.webapp.verticalascent.repository.ProductRepository;
import com.webapp.verticalascent.repository.ShoppingSessionRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;


/**
 * Service implementation with logic for Shopping Session.
 *
 * @author Nathan L.
 * @version 1.0
 */
@Service
public class ShoppingSessionService {
	
	private final ShoppingSessionRepository shoppingSessionRepository;
	private final CartProductService cartProductService;
	private final ProductService productService;
	
	@Autowired
	public ShoppingSessionService(
		ShoppingSessionRepository shoppingSessionRepository,
		CartProductService cartProductService,
		ProductService productService
		
	) {
		this.shoppingSessionRepository = shoppingSessionRepository;
		this.cartProductService = cartProductService;
		this.productService = productService;
	}
	
	/**
	 * Check if there is a shopping session active for the given User session id.
	 *
	 * @param sessionId The id of user's session.
	 */
	public ShoppingSession isShoppingSessionActive(String sessionId) {
		return shoppingSessionRepository.findBySessionIDAndIsActive(sessionId, true);
	}
	
	public void userShoppingSession(String sessionId, List<ProductDto> cartItems) {
		ShoppingSession userShoppingSession = isShoppingSessionActive(sessionId);
		
		if (userShoppingSession != null) {
			handleExistingShoppingSession(userShoppingSession, cartItems);
		} else {
			createNewShoppingSession(sessionId, cartItems);
		}
	}
	
	private void handleExistingShoppingSession(ShoppingSession userShoppingSession, List<ProductDto> cartItems) {
		for (ProductDto cartItem : cartItems) {
			Optional<Product> productOptional = productService.findOneById(cartItem.getId());
			
			if (productOptional.isPresent()) {
				Product product = productOptional.get();
				CartProduct existingCartProductAndSession = cartProductService.getCartItemBySessionAndProduct(userShoppingSession, product);
				
				if (existingCartProductAndSession != null) {
					updateExistingCartProduct(existingCartProductAndSession, cartItem);
				} else {
					cartProductService.newCartProduct(cartItem.getId(), userShoppingSession);
				}
			}
		}
	}
	
	private void updateExistingCartProduct(CartProduct existingCartProductAndSession, ProductDto cartItem) {
		if(cartItem.getQuantity() <= 0 ) {
			existingCartProductAndSession.setQuantity(0);
		} else {
			existingCartProductAndSession.setQuantity(existingCartProductAndSession.getQuantity() + cartItem.getQuantity());
		}
		existingCartProductAndSession.setTotalPrice(BigDecimal.valueOf(existingCartProductAndSession.getQuantity() * cartItem.getPrice()));
		existingCartProductAndSession.setModifiedAt(new Date());
		cartProductService.savedCartProduct(existingCartProductAndSession);
	}
	
	private void createNewShoppingSession(String sessionId, List<ProductDto> cartItems) {
		ShoppingSession newUserShoppingSess = new ShoppingSession();
		newUserShoppingSess.setCreatedAt(new Date());
		newUserShoppingSess.setExpirationDate(new Date());
		newUserShoppingSess.setSessionID(sessionId);
		newUserShoppingSess.setIsActive(true);
		
		List<CartProduct> newCartProducts = createNewCartProducts(newUserShoppingSess, cartItems);
		BigDecimal totalPrice = calculateTotalPrice(newCartProducts);
		
		newUserShoppingSess.setCartProducts(newCartProducts);
		newUserShoppingSess.setTotalPrice(totalPrice);
		
		ShoppingSession savedSession = shoppingSessionRepository.save(newUserShoppingSess);
		
		for (CartProduct newCartProduct : newCartProducts) {
			newCartProduct.setShoppingSession(savedSession);
			cartProductService.savedCartProduct(newCartProduct);
		}
	}
	
	private List<CartProduct> createNewCartProducts(ShoppingSession newUserShoppingSess, List<ProductDto> cartItems) {
		List<CartProduct> newCartProducts = new ArrayList<>();
		for (ProductDto newCartItem : cartItems) {
			CartProduct cartProduct = new CartProduct();
			cartProduct.setShoppingSession(newUserShoppingSess);
			cartProduct.setProduct(productService.findOneById(newCartItem.getId()).orElseThrow());
			cartProduct.setCreatedAt(new Date());
			cartProduct.setQuantity(newCartItem.getQuantity());
			cartProduct.setTotalPrice(BigDecimal.valueOf(newCartItem.getQuantity() * newCartItem.getPrice()));
			newCartProducts.add(cartProduct);
		}
		return newCartProducts;
	}
	
	private BigDecimal calculateTotalPrice(List<CartProduct> newCartProducts) {
		BigDecimal totalPrice = BigDecimal.ZERO;
		for (CartProduct cartProduct : newCartProducts) {
			totalPrice = totalPrice.add(cartProduct.getTotalPrice());
		}
		return totalPrice;
	}
	
	/**
	 * Check if the product from the local storage are valid product.
	 *
	 * @param cartItems List of product from the local storage.
	 * @return List<ProductDto> validatedItems || empty (can be empty).
	 */
	public List<ProductDto> validateCartItems(List<ProductDto> cartItems) {
		List<ProductDto> validatedItems = new ArrayList<>();
		
		for (ProductDto cartItem : cartItems) {
			Optional<Product> productOptional = productService.findOneById(cartItem.getId());
			
			if (productOptional.isPresent()) {
				Product product = productOptional.get();
				
				if (isValidProduct(cartItem, product)) {
					validatedItems.add(cartItem);
				}
			}
		}
		return validatedItems;
	}
	
	private boolean isValidProduct(ProductDto cartItem, Product product) {
			return cartItem.getName().equals(product.getName())
				&& Objects.equals(cartItem.getPrice(), product.getPrice())
				&& cartItem.getQuantity() <= product.getQuantity();
		}
	
	@Scheduled(cron = "0 0 0 * * *") // Tous les jours à minuit
	public void checkSessionExpiration() {
		List<ShoppingSession> sessions = shoppingSessionRepository.findAll();
		Date currentDate = new Date();
		
		for (ShoppingSession session : sessions) {
			if (session.getExpirationDate().before(currentDate)) {
				session.setIsActive(false);
				shoppingSessionRepository.save(session);
			}
		}
	}
	

}
