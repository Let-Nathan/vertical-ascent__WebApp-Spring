package com.webapp.verticalascent.service;

import com.webapp.verticalascent.entity.ProductCategory;
import java.util.List;
import com.webapp.verticalascent.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implementation with logic for ProductCategory
 *
 * @author Nathan L.
 * @version 1.0
 */
@Service
public class ProductCategoryService {
	
	private final ProductCategoryRepository ProductCategoryRepository;
	
	@Autowired
	public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
		this.ProductCategoryRepository = productCategoryRepository;
	}
	
	/**
	*
	* @return ProductCategory
	*/
	public List<ProductCategory> getAllProductCategory() {
		return ProductCategoryRepository.findAll();
	}
	
}
