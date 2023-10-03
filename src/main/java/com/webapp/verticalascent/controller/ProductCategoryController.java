package com.webapp.verticalascent.controller;

import com.webapp.verticalascent.entity.ProductCategory;
import com.webapp.verticalascent.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product-categories")
public class ProductCategoryController {
	
	private final ProductCategoryRepository productCategoryRepository;
	
	@Autowired
	public ProductCategoryController(ProductCategoryRepository productCategoryRepository) {
		this.productCategoryRepository = productCategoryRepository;
	}
	
	@GetMapping
	public List<ProductCategory> getAllCategories() {
		return productCategoryRepository.findAll();
	}
}
