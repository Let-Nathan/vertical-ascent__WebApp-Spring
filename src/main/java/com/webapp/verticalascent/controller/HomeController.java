package com.webapp.verticalascent.controller;

import com.webapp.verticalascent.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class for Home path.
 *
 * @author Nathan L.
 * @version 1.0
 *
 */
@Controller
public class HomeController {
    
    private final ProductCategoryService productCategoryService;
    
    /**
     * Dependency injection for userRepository ==> @Todo to userDTO.
     *
     * @param productCategoryService (ProductCategoryService)
     */
    @Autowired
    public HomeController(final ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }
    
    /**
     * Default home path ==> @Todo Router config file.
     *
     * @return The view name
     *
     */
    @GetMapping("/")
    public final String home() {
        return "home";
    }
    
    @GetMapping("/dino")
    public final String game() {
        return "fragments/_dino-game";
    }
    
}
