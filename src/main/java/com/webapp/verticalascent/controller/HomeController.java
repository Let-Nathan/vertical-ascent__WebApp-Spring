package com.webapp.verticalascent.controller;

import com.webapp.verticalascent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UserRepository userRep;

    @Autowired
    public HomeController(final UserRepository userRep) {
        this.userRep = userRep;
    }

    @GetMapping("/")
    public final String home(Model model){
        model.addAttribute("grettings", "Hello world Model View");
        model.addAttribute("users", userRep.findAll());
        return "home";
    }
}
