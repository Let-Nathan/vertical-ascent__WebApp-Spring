package com.webapp.verticalascent.service;

import com.webapp.verticalascent.dto.ProductDto;
import com.webapp.verticalascent.entity.CartProduct;
import com.webapp.verticalascent.entity.Product;
import com.webapp.verticalascent.entity.ShoppingSession;
import com.webapp.verticalascent.repository.CartProductRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	 * Creat a new cart product and link it to the user's shopping sess.
	 *
	 * @param newUserShoppingSess ShoppingSession
	 * @param cartItems List of product dto
	 * @return List CartProduct
	 */
	public List<CartProduct> createNewCartProducts(
		ShoppingSession newUserShoppingSess,
		List<ProductDto> cartItems
	) {
		List<CartProduct> newCartProducts = new ArrayList<>();
		
		try {
			for (ProductDto newCartItem : cartItems) {
				CartProduct cartProduct = new CartProduct();
				cartProduct.setShoppingSession(newUserShoppingSess);
				cartProduct.setProduct(productService.findOneById(newCartItem.getId()).orElseThrow());
				cartProduct.setCreatedAt(new Date());
				cartProduct.setQuantity(newCartItem.getQuantity());
				cartProduct.setTotalPrice(BigDecimal.valueOf(newCartItem.getQuantity() * newCartItem.getPrice()));
				newCartProducts.add(cartProduct);
			}
			
			// Maintenant, on retourne simplement la liste des CartProduct créés
			return newCartProducts;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * Check if there is already a Cart Product associated to the Shopping Session.
	 *
	 * @param shoppingSession ShoppingSession
	 * @param product Product
	 * @return CartProduct object
	 */
	public CartProduct getCartItemBySessionAndProduct(ShoppingSession shoppingSession, Product product) {
		return cartProductRepository.findByShoppingSessionAndProductAndShoppingSessionIsActive(shoppingSession, product, true);
	}
	
	/**
	 * Return a list of Cart Product based on Shopping Session.
	 *
	 * @param shoppingSession ShoppingSession
	 * @return List of CartProduct
	 */
	public List<CartProduct> getCartProductListBySession(ShoppingSession shoppingSession) {
		int minQuantity = 1;
		return cartProductRepository.findAllByShoppingSessionGreaterThan(shoppingSession, minQuantity);
	}
	
	/**
	 * Save the CarProduct into Database.
	 *
	 * @param cartProduct CartProduct
	 */
	public void savedOneCartProduct(CartProduct cartProduct) {
		 cartProductRepository.save(cartProduct);
	}
	
	/**
	 * Saved a list of Cart Product in database.
	 *
	 * @param cartProducts List CartProduct
	 */
	public void savedListCartProducts(List<CartProduct> cartProducts) {
		// Save the list of cart products
		try {
			cartProductRepository.saveAll(cartProducts);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * Updated an existing CartProduct (quantity, total price, modified at) and store new value into Database.
	 *
	 * @param existingCartProduct CartProduct
	 * @param cartItem ProductDto
	 */
	public CartProduct updateExistingCartProduct(CartProduct existingCartProduct, ProductDto cartItem) {
		if (cartItem.getQuantity() <= 0) {
			existingCartProduct.setQuantity(0);
		} else {
			existingCartProduct.setQuantity(cartItem.getQuantity());
		}
		existingCartProduct.setTotalPrice(BigDecimal.valueOf(cartItem.getQuantity() * cartItem.getPrice()));
		existingCartProduct.setModifiedAt(new Date());
		savedOneCartProduct(existingCartProduct);
		return existingCartProduct;
	}

	/**
	 * Check if the product from the local storage are valid product.
	 *
	 * @param cartItems List of product from the local storage.
	 * @return List ProductDto validatedItems || empty (can be empty).
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
	
	/**
	 * Check if the current ProductDto match a product in Database.
	 *
	 * @param cartItem ProductDto
	 * @param product Product
	 * @return Boolean True if a product match current characteristics (price, quantity, name).
	 */
	private Boolean isValidProduct(ProductDto cartItem, Product product) {
		return cartItem.getName().equals(product.getName())
			&& Objects.equals(cartItem.getPrice(), product.getPrice())
			&& cartItem.getQuantity() <= product.getQuantity();
	}
}
