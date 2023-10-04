package com.webapp.verticalascent.service;

import com.webapp.verticalascent.entity.ProductCategory;
import com.webapp.verticalascent.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation with logic for ProductCategory
 *
 * @author Nathan L.
 * @version 1.0
 */
@Service
public class ProductCategoryService {
	
	private final ProductCategoryRepository productCategoryRepository;
	
	/**
	* Dependency injection for userRepository
	*
	* @param productCategoryRepository (Data access)
	*
	*/
	@Autowired
	public ProductCategoryService (ProductCategoryRepository productCategoryRepository) {
		this.productCategoryRepository = productCategoryRepository;
	}
	
	/**
	*
	* @return List<ProductCategory>
	*/
	public List<ProductCategory> getAllProductCategory() {
		return productCategoryRepository.findAll();
	}
	
}
