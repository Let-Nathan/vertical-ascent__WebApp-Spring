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
public class ProductCategoryController {
	
	@GetMapping("/product-category/{id}")
	public final String displayProduct() {
		return "product";
	}
	
}
