package com.webapp.verticalascent.service;

import com.webapp.verticalascent.entity.CartProduct;
import com.webapp.verticalascent.entity.ShoppingSession;
import com.webapp.verticalascent.entity.User;
import com.webapp.verticalascent.repository.ShoppingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;


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
	
	@Autowired
	public ShoppingSessionService(
		ShoppingSessionRepository shoppingSessionRepository,
		CartProductService cartProductService
	) {
		this.shoppingSessionRepository = shoppingSessionRepository;
		this.cartProductService = cartProductService;
	}
	
	/**
	 * Check if there is a shopping session active for the given User object.
	 *
	 * @param user User object representation.
	 */
	public ShoppingSession isShoppingSessionActive(User user) {
		return shoppingSessionRepository.findByUserAndIsActive(user, true);
	}
	
//	public BigDecimal calcultotalPrice(List<CartProduct> cartProductList) {
//		BigDecimal totalSessionPrice = BigDecimal.ZERO;
//		for (CartProduct cartProduct : cartProductList) {
//			totalSessionPrice = totalSessionPrice.add(cartProduct.getTotalPrice());
//		}
//		return totalSessionPrice;
//	}
//
//	public void newShoppingSession(User user, List<CartProduct> cartProductList) {
//		if(isShoppingSessionActive(user) != null) {
//
//		} else {
//			ShoppingSession shoppingSession = new ShoppingSession();
//			shoppingSession.setUser(user);
//			shoppingSession.setCartProducts(cartProductList);
//			shoppingSession.setTotalPrice(calcultotalPrice(cartProductList));
//			shoppingSession.setIsActive(true);
//			cartProductService.newCartProduct();
//			shoppingSessionRepository.save((shoppingSession));
//		}
//	}

}
