package com.webapp.verticalascent.controller;

import com.webapp.verticalascent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class for Home path
 *
 * @author Nathan L.
 * @version 1.0
 *
 */
@Controller
public class HomeController {

    private final UserRepository userRep;
	
    /**
    * Dependency injection for userRepository ==> @Todo to userDTO.
    *
    * @param userRep (User repository)
    */
    @Autowired
    public HomeController(final UserRepository userRep) {
        this.userRep = userRep;
    }
	
    /**
    * Default home path ==> @Todo Router config file.
    *
    * @param model (Model injection to view variable)
    * @return The view name
    *
    */
    @GetMapping("/")
    public final String home(Model model) {
        model.addAttribute("grettings", "Hello world Model View");
        model.addAttribute("users", userRep.findAll());
        return "home";
    }
}
