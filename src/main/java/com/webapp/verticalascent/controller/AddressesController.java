package com.webapp.verticalascent.controller;

import com.webapp.verticalascent.dto.AddressDto;
import com.webapp.verticalascent.entity.Address;
import com.webapp.verticalascent.entity.User;
import com.webapp.verticalascent.service.AddressesService;
import com.webapp.verticalascent.service.UserService;
import jakarta.validation.Valid;
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
     * Display the new address form.
     *
     * @return The view name
     */
    @GetMapping("/new-address")
    public final String home(
        Model model,
        @AuthenticationPrincipal UserDetails userDetails
    
    ) {
        String userEmail = userDetails.getUsername();
        User user = userService.isEmailExist(userEmail);
        if(addressesService.getUserAddresses(user) != null) {
            return "redirect:/account";
        }
        AddressDto addressDto = new AddressDto();
        model.addAttribute("addressDto", addressDto);
        
        return "user-address";
    }
    
    /**
     * Add a new address linked to the current user.
     *
     * @param addressDto AddessDto perform address validation
     * @param bindingResult return errors to the user
     * @param userDetails check if the user is register
     * @return view
     */
    @PostMapping("/add-address")
    public String addAddress(
        @Valid @ModelAttribute AddressDto addressDto,
        BindingResult bindingResult,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        
        if (bindingResult.hasErrors()) {
            return "user-address";
        } else if (userService.isEmailExist(userDetails.getUsername()) == null) {
            return "redirect:/login";
        } else {
                String userEmail = userDetails.getUsername();
                User user = userService.isEmailExist(userEmail);
                // Si aucune erreur, traiter l'ajout de l'adresse et rediriger vers une page appropriée
                Address address = addressesService.convertAddressDtoToAddress(addressDto);
                addressesService.saveAddress(addressesService.linkAddresseToUser(address, user));
                return "redirect:/account";
        }
        
    }
    
}
