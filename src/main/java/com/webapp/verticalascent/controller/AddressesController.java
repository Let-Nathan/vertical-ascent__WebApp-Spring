package com.webapp.verticalascent.controller;

import com.webapp.verticalascent.entity.ProductCategory;
import com.webapp.verticalascent.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


/**
 * Controller class for Home path.
 *
 * @author Nathan L.
 * @version 1.0
 *
 */
@Controller
public class AddressesController {
    
    /**
     * Dependency injection for userRepository ==> @Todo to userDTO.
     *
     * @param productCategoryService (ProductCategoryService)
     */
    @Autowired
    public AddressesController(ProductCategoryService productCategoryService) {
    }
    
    /**
     *
     *
     * @return The view name
     */
    @GetMapping("/nouvelle-adresse")
    public final String home() {
       return "user-addresses";
    }
    
}
