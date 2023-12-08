package com.webapp.verticalascent.service;

import com.webapp.verticalascent.dto.ProductDto;
import com.webapp.verticalascent.entity.CartProduct;
import com.webapp.verticalascent.entity.Product;
import com.webapp.verticalascent.entity.ShoppingSession;
import com.webapp.verticalascent.repository.ShoppingSessionRepository;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
	 * @return boolean true if session active found.
	 */
	public Boolean isShoppingSessionActive(String sessionId) {
		return shoppingSessionRepository.findBySessionIdAndIsActive(sessionId, true) != null;
	}
	
	/**
	 * Return true if we find a shopping session and the shopping process is not completed.
	 *
	 * @param sessionId The id of user's session.
	 * @return Boolean true of session Exist and shopping process not completed.
	 */
	public Boolean isShoppingSessionExistAndShoppingProcessNotEnd(String sessionId) {
		try {
			return shoppingSessionRepository.findBySessionIdAndIsShoppingProcessEnd(sessionId, false) != null;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * Return a Shopping Session if one correspond to the session id parameters.
	 *
	 * @param sessionId The id of user's session.
	 * @return ShoppingSession
	 */
	public ShoppingSession getShoppingSession(String sessionId) {
		return shoppingSessionRepository.findBySessionId(sessionId);
	}
	
	/**
	 * Active an inactive Shopping Session.
	 *
	 * @param sessionId The id of user's session.
	 * @return ShoppingSession
	 */
	public ShoppingSession activeShoppingSession(String sessionId) {
		ShoppingSession shoppingSession = shoppingSessionRepository.findBySessionIdAndIsActive(sessionId, false);
		shoppingSession.setIsActive(true);
		shoppingSession.setExpirationDate(newExpirationDate());
		return shoppingSession;
	}
	
	/**
	 * Set the new expiration date to : today + 1 day.
	 *
	 * @return Date + 1 day
	 */
	private Date newExpirationDate() {
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}
	
	/**
	 * Used to update an existing shopping session and associated potential cart product.
	 *
	 * @param userShoppingSession  The id of user's Shopping Session.
	 * @param cartItems List of product dto.
	 */
	public void handleExistingShoppingSession(String userShoppingSession, List<ProductDto> cartItems) {
	
		for (ProductDto cartItem : cartItems) {
			// We store one Product DTO to an Optional Product Object, to verify that is matching a product in Database.
			Optional<Product> productOptional = productService.findOneById(cartItem.getId());
			// If so, we create a new Product object.
			if (productOptional.isPresent()) {
				Product product = productOptional.get();
				// Find if there is an existing Cart Product associated to the session.
				CartProduct existingCartProductAndSession = cartProductService.getCartItemBySessionAndProduct(
					getShoppingSession(userShoppingSession),
					product
				);
				if (existingCartProductAndSession != null) {
					// User has already a Shopping Session with a similar product, so we update it.
					cartProductService.updateExistingCartProduct(existingCartProductAndSession, cartItem);
				} else {
					// User hase no Cart Product associated to the Shopping Session, so we creat one.
					cartProductService.createNewCartProducts(
						getShoppingSession(userShoppingSession),
						cartItems);
				}
			}
		}
	}
	
	/**
	 * Create a new Shopping Session with associated cart product for an anonymous user.
	 *
	 * @param sessionId The id of user's session.
	 * @param cartItems List of product dto from local storage.
	 */
	public void createNewShoppingSession(String sessionId, List<ProductDto> cartItems) {
		// Build the new shopping session
		ShoppingSession newUserShoppingSession = buildNewUserShoppingSession(sessionId);
		// Create new carts products with the List cartItems and linked it to the new anonymous shopping sess
		List<CartProduct> newCartProducts = cartProductService.createNewCartProducts(newUserShoppingSession, cartItems);
		// Link the List of Cart Product to the current Shopping Session.
		newUserShoppingSession.setCartProducts(newCartProducts);
		newUserShoppingSession.setTotalPrice(calculateTotalPrice(newCartProducts));
		// Persist the Anonymous Shopping Sess in database.
		shoppingSessionRepository.save(newUserShoppingSession);
	}
	
	/**
	 * Build a new Shopping Session for the anonymous user.
	 *
	 * @param sessionId The id of user's session.
	 * @return ShoppingSession
	 */
	private ShoppingSession buildNewUserShoppingSession(String sessionId) {
		ShoppingSession newUserShoppingSession = new ShoppingSession();
		newUserShoppingSession.setCreatedAt(new Date());
		newUserShoppingSession.setExpirationDate(newExpirationDate());
		newUserShoppingSession.setSessionId(sessionId);
		newUserShoppingSession.setIsActive(true);
		return newUserShoppingSession;
	}
	
	/**
	 * Total price of a List CartProduct.
	 *
	 * @param newCartProducts List of cart product from the anonymous user.
	 * @return BigDecimal totalPrice of List.
	 */
	private BigDecimal calculateTotalPrice(List<CartProduct> newCartProducts) {
		BigDecimal totalPrice = BigDecimal.ZERO;
		for (CartProduct cartProduct : newCartProducts) {
			totalPrice = totalPrice.add(cartProduct.getTotalPrice());
		}
		return totalPrice;
	}
	
	/**
	 * Scheduled task to check if a session need to expire.
	 * If so, set isActive to false.
	 */
	@Scheduled(cron = "0 0 0 * * *") // All minutes to check if ok
	private void checkSessionExpiration() {
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
