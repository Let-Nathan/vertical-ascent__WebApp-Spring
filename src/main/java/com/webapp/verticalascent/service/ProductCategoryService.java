package com.webapp.verticalascent.service;

import com.webapp.verticalascent.entity.ProductCategory;
import com.webapp.verticalascent.repository.ProductCategoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implementation with logic for ProductCategory.
 *
 * @author Nathan L.
 * @version 1.0
 */
@Service
public class ProductCategoryService {
	
	private final ProductCategoryRepository productCategoryRepository;
	
	@Autowired
	public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
		this.productCategoryRepository = productCategoryRepository;
	}
	
	/**
	* Get all the product category from the repository.
	*
	* @return ProductCategory
	*/
	public List<ProductCategory> getAllProductCategory() {
		return productCategoryRepository.findAll();
	}
	
	/**
	 * Get one product category from the repository.
	 *
	 * @return ProductCategory unique category based on name.
	 */
	public ProductCategory getOneCategoyByName(String categoryName) {
		return productCategoryRepository.findByName(categoryName);
	}
	
}
