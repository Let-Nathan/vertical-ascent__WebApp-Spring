package com.webapp.verticalascent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class for Login.
 *
 * @author Nathan L.
 * @version 1.0
 *
 */
@Controller
public class ProductController {
	
	@GetMapping("/product-category/{id}")
	public final String displayProduct() {
		return "products-list";
	}
	
	@GetMapping("/categories")
	public final String displayCategories() {
		return "categories";
	}
	
}
