package com.webapp.verticalascent.controller;

import com.webapp.verticalascent.config.SecurityConfig;
import com.webapp.verticalascent.entity.ProductCategory;
import com.webapp.verticalascent.service.ProductCategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


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
    public HomeController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }
    
    /**
     * Default home path ==> @Todo Router config file.
     *
     * @return The view name
     *
     */
    @GetMapping("/")
    public final String home(Model model, @AuthenticationPrincipal UserDetails user) {
        
        List<ProductCategory> productCategories = productCategoryService.getAllProductCategory();
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }

        model.addAttribute("productCategories", productCategories);
        return "home";
    }
    
}
