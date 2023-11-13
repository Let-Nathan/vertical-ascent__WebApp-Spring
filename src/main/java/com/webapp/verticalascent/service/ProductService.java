package com.webapp.verticalascent.service;

import com.webapp.verticalascent.entity.Product;
import com.webapp.verticalascent.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implementation with logic for Product.
 *
 * @author Nathan L.
 * @version 1.0
 */
@Service
public class ProductService {
	
	private final ProductRepository productRepository;
	
	@Autowired
	public ProductService(
		ProductRepository productRepository
	) {
		this.productRepository = productRepository;
	}
	
	/**
	 * Take id and return a list of Product associated to category.
	 *
	 * @param categoryName string name of a category
	 */
	public List<Product> findAllProduct(String categoryName) {
		return productRepository.findByCategoryName(categoryName);
	}
	
	public Optional<Product> findOneById(Long id) {
		return productRepository.findById(id);
	}
	
	
}
