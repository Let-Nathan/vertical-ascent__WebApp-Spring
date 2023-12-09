package com.webapp.verticalascent.controller;

import javax.servlet.http.HttpServletRequest;
import com.webapp.verticalascent.service.AddressesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller class for Home path.
 *
 * @author Nathan L.
 * @version 1.0
 *
 */
@Controller
public class AddressesController {
    
    
    private final AddressesService addressesService;
    
    @Autowired
    public AddressesController(AddressesService addressesService) {
        this.addressesService = addressesService;
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
    
    @PostMapping("/add-address")
    public String ajouterAdresse(
        @Valid @ModelAttribute AdresseDTO adresseDTO,
        BindingResult bindingResult,
        RedirectAttributes redirectAttributes,
        HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            // Des erreurs de validation sont présentes, renvoyer le formulaire avec les erreurs
            return "user-addresses";
        } else {
            // Pas d'erreurs de validation, sauvegarder l'adresse
            Adresse adresse = addressesService.convertDTOToAdresse(adresseDTO); // Convertir DTO en objet Adresse
            addressesService.sauvegarderAdresse(adresse);
            
            // Redirection vers l'URL précédente
            String referer = request.getHeader("Referer");
            if (referer != null && !referer.isEmpty()) {
                return "redirect:" + referer;
            } else {
                return "redirect:/";
            }
        }
    }
    
}
