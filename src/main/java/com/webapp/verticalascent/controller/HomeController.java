package com.webapp.verticalascent.controller;

import com.webapp.verticalascent.entity.ProductCategory;
import com.webapp.verticalascent.service.ProductCategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    private final AuthenticationManager authenticationManager;
    /**
     * Dependency injection for userRepository ==> @Todo to userDTO.
     *
     * @param productCategoryService (ProductCategoryService)
     */
    @Autowired
    public HomeController(ProductCategoryService productCategoryService, AuthenticationManager authenticationManager) {
        this.productCategoryService = productCategoryService;
        this.authenticationManager = authenticationManager;
    }
    
    /**
     * Default home path ==> @Todo Router config file.
     *
     * @return The view name
     *
     */
    @GetMapping("/")
    public final String home(Model model) {
        List<ProductCategory> productCategories = productCategoryService.getAllProductCategory();
        model.addAttribute("productCategories", productCategories);
        return "home";
    }
    
    @GetMapping("/login")
    public final String login() {
        return "login";
    }
    
    @PostMapping("/perform_login")
    public String performLogin(@RequestParam String email, @RequestParam String password, Model model) throws BadCredentialsException {
        // Créer une instance de UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);
        
        try {
            // Authentifier l'utilisateur
            Authentication authentication = authenticationManager.authenticate(authToken);
            // Vérifier si l'authentification est réussie
            if (authentication.isAuthenticated()) {
                // Définir l'authentification dans le contexte de sécurité
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // Redirection en cas d'authentification réussie
                return "redirect:/home";
            }
        } catch (BadCredentialsException e) {
            // En cas d'échec de l'authentification, récupérez le message d'erreur
            String errorMessage = e.getMessage();
            // Ajoutez le message d'erreur au modèle
            model.addAttribute("error", errorMessage);
        }
        
        // Redirection vers la page de connexion avec un message d'erreur
        return "login";
    }
}
