package com.webapp.verticalascent.controller;

import com.webapp.verticalascent.entity.Product;
import com.webapp.verticalascent.service.ProductCategoryService;
import com.webapp.verticalascent.service.ProductService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Controller class for Login.
 *
 * @author Nathan L.
 * @version 1.0
 *
 */
@Controller
public class    ProductController {
	
	private final ProductService productService;
	private final ProductCategoryService productCategoryService;
	
	@Autowired
	public ProductController(
		ProductService productService,
		ProductCategoryService productCategoryService
	) {
		this.productService = productService;
		this.productCategoryService = productCategoryService;
	}
	
	/**
	 * Method to display all product from a category name.
	 *
	 * @param model Model add attribute to the view
	 * @param categoryName String url category name
	 * @return view product-list
	 */
	@GetMapping("/product-category/{categoryName}")
	public final String productCategory(
		Model model,
		@PathVariable String categoryName
	) {
		List<Product> products = productService.findAllProduct(categoryName);
		model.addAttribute("products", products);
		model.addAttribute(
			"category",
			productCategoryService.getOneCategoyByName(categoryName)
		);
		return "products-list";
	}
	
	/**
	 * Display product based on his id.
	 *
	 * @param id Long id to find product in database.
	 * @param model Model add attributes to the view
	 * @return view product-details.
	 */
	@GetMapping("/product/{id}")
	public final String productDetails(
		@PathVariable Long id,
		Model model
	) {
		Optional<Product> productOptional = productService.findOneById(id);
		try {
			if (productOptional.isPresent()) {
				Product product = productOptional.get();
				model.addAttribute("product", product);
			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "product-details";
	}
}
