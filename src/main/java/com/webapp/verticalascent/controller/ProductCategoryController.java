package com.webapp.verticalascent.controller;

import com.webapp.verticalascent.entity.ProductCategory;
import com.webapp.verticalascent.service.ProductCategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API REST Product Categories.
 *
 * @author Nathan L
 * @version 1.0
 *
 */
@RestController
@RequestMapping("/api/product-categories")
public class ProductCategoryController {
 
	private final ProductCategoryService productCategoryService;
	
    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService) {
	    this.productCategoryService = productCategoryService;
	}
	
    @GetMapping
    public List<ProductCategory> getAllCategories() {
	    return productCategoryService.getAllProductCategory();
	}
	
	
}
