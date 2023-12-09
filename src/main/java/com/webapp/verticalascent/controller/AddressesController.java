package com.webapp.verticalascent.controller;

import com.webapp.verticalascent.entity.Addresses;
import com.webapp.verticalascent.entity.User;
import com.webapp.verticalascent.service.UserService;
import jakarta.validation.Valid;
import com.webapp.verticalascent.dto.AddressesDto;
import jakarta.servlet.http.HttpServletRequest;
import com.webapp.verticalascent.service.AddressesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/adresse")
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
    @GetMapping("/nouvelle-adresse")
    public final String home() {
       return "user-addresses";
    }
    
    @PostMapping("/add-address")
    public String ajouterAdresse(
        @Valid @ModelAttribute AddressesDto addressesDto,
        BindingResult bindingResult,
        HttpServletRequest request,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (bindingResult.hasErrors()) {
            // If there is errors in addresses dto, we return them to user to make him correct and submit again
            return "user-addresses";
        } else if (userService.isEmailExist(userDetails.getUsername()) == null) {
            return "redirect:/login";
        } else {
            String userEmail = userDetails.getUsername();
            User user = userService.isEmailExist(userEmail);
            // If there is no errors, we convert addressesDto to Addresse and associated it to the user
            Addresses addresses = addressesService.convertAddresseDtoToAddresse(addressesDto);
            addressesService.linkAddresseToUser(addresses, user);
            // Redirect
            String referer = request.getHeader("Referer");
            if (referer != null && !referer.isEmpty()) {
                return "redirect:" + referer;
            } else {
                return "redirect:/";
            }
        }
    }
    
}
