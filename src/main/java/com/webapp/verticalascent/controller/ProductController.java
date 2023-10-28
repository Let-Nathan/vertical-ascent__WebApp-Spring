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
	public final String productCategory() {
		return "products-list";
	}
	
	@GetMapping("/product/{id}")
	public final String productDetails() {
		return "product-details";
	}
}
