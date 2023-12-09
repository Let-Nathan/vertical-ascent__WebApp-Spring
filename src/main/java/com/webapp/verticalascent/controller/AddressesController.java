package com.webapp.verticalascent.controller;

import com.webapp.verticalascent.entity.Address;
import com.webapp.verticalascent.entity.User;
import com.webapp.verticalascent.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import com.webapp.verticalascent.dto.AddressDto;
import com.webapp.verticalascent.service.AddressesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller class for Home path.
 *
 * @author Nathan L.
 * @version 1.0
 *
 */
@RequestMapping("/address")
@Controller
public class AddressesController {
    
    
    private final AddressesService addressesService;
    private final UserService userService;
    
    @Autowired
    public AddressesController(AddressesService addressesService, UserService userService) {
        this.addressesService = addressesService;
        this.userService = userService;
    }
    
    /**
     *
     *
     * @return The view name
     */
    @GetMapping("/new-address")
    public final String home(
        Model model
    ) {
        AddressDto addressDto = new AddressDto();
        model.addAttribute("addressDto", addressDto);
        return "user-address";
    }
    
    @PostMapping("/add-address")
    public String addAddress(
        @Valid @ModelAttribute AddressDto addressDto,
        BindingResult bindingResult,
        HttpServletRequest request,
        @AuthenticationPrincipal UserDetails userDetails,
        RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return "user-address"; // Rediriger vers le formulaire avec les erreurs
        } else if (userService.isEmailExist(userDetails.getUsername()) == null) {
            return "redirect:/login";
        } else {
            String userEmail = userDetails.getUsername();
            User user = userService.isEmailExist(userEmail);
            // Si aucune erreur, traiter l'ajout de l'adresse et rediriger vers une page appropriée
            Address address = addressesService.convertAddresseDtoToAddresse(addressDto);
            addressesService.saveAddress(addressesService.linkAddresseToUser(address, user));
            String referer = request.getHeader("Referer");
            if (referer != null && !referer.isEmpty()) {
                return "redirect:" + referer;
            } else {
                return "redirect:/account";
            }
        }
    }
    
}
