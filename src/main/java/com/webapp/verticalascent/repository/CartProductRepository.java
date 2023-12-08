package com.webapp.verticalascent.repository;

import com.webapp.verticalascent.entity.CartProduct;
import com.webapp.verticalascent.entity.Product;
import com.webapp.verticalascent.entity.ShoppingSession;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Access and manage "Addresses" data.
 *
 * @author Nathan L
 * @version 1.0
 */
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
	CartProduct findByShoppingSessionAndProductAndShoppingSessionIsActive(
		ShoppingSession shoppingSession,
		Product product,
		Boolean isActive
	);
	
	List<CartProduct> findAllByShoppingSession(ShoppingSession shoppingSession);
}
