package com.webapp.verticalascent.service;

import com.webapp.verticalascent.dto.ProductDto;
import com.webapp.verticalascent.entity.CartProduct;
import com.webapp.verticalascent.entity.Product;
import com.webapp.verticalascent.entity.ShoppingSession;
import com.webapp.verticalascent.repository.CartProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * Service implementation with logic for Shopping Session.
 *
 * @author Nathan L.
 * @version 1.0
 */
@Service
public class CartProductService {
	
	private final CartProductRepository cartProductRepository;
	private final ProductService productService;
	
	@Autowired
	public CartProductService(
		CartProductRepository cartProductRepository,
		ProductService productService
		) {
		this.cartProductRepository = cartProductRepository;
		this.productService = productService;
	}
	
	/**
	 * Create a new Cart Product
	 * @param id
	 * @param shoppingSession
	 */
	public void newCartProduct(Long id, ShoppingSession shoppingSession) {
		Optional<Product> productOptional = productService.findOneById(id);
		
		if(productOptional.isPresent() && productService.isInStock(productOptional.get())) {
			CartProduct cartProduct = new CartProduct();
			cartProduct.setProduct(productOptional.get());
			cartProduct.setTotalPrice(BigDecimal.valueOf(productOptional.get().getPrice()));
			cartProduct.setShoppingSession(shoppingSession);
			cartProductRepository.save(cartProduct);
		} else {
			throw new IllegalArgumentException(
				"Le produit n'est pas en stock actuellement. Vous pouvez l'ajouter à votre liste d'envie en attendant."
			);
		}
	}
	
	public CartProduct getCartItemBySessionAndProduct(ShoppingSession shoppingSession, Product product) {
		return cartProductRepository.findByShoppingSessionAndProductAndShoppingSessionIsActive(shoppingSession, product, true);
	}
	
	public void savedCartProduct(CartProduct cartProduct) {
		 cartProductRepository.save(cartProduct);
	}
	
	

}
