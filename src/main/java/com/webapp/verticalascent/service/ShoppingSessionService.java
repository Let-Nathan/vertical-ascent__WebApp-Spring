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
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Service implementation with logic for Shopping Session.
 *
 * @author Nathan L.
 * @version 1.0
 */
@Service
public class ShoppingSessionService {
	
	private final ShoppingSessionRepository shoppingSessionRepository;
	private final ProductRepository productRepository;
	
	@Autowired
	public ShoppingSessionService(
		ShoppingSessionRepository shoppingSessionRepository,
		ProductRepository productRepository,
		CartProductService cartProductService
	) {
		this.shoppingSessionRepository = shoppingSessionRepository;
		this.productRepository = productRepository;
	}
	
	/**
	 * Check if there is a shopping session active for the given User object.
	 *
	 * @param user User object representation.
	 */
	public ShoppingSession isShoppingSessionActive(User user) {
		return shoppingSessionRepository.findByUserAndIsActive(user, true);
	}
	
	public List<ProductDto> validateCartItems(List<ProductDto> cartItems) {
		List<ProductDto> validatedItems = new ArrayList<>();
		
		for (ProductDto cartItem : cartItems) {
			Optional<Product> productOptional = productRepository.findById(cartItem.getId());
			if (productOptional.isPresent()) {
				Product product = productOptional.get();
				if (product.getName().equals(cartItem.getName()) && product.getPrice().equals(cartItem.getPrice())) {
					validatedItems.add(cartItem);
				} else {
					throw new ValidationException("Produit invalide : " + cartItem.getName());
				}
			}
		}
		
		return validatedItems;
	}
	

}
