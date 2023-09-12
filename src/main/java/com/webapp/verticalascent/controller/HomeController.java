package com.webapp.verticalascent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public final String home(Model model){
        model.addAttribute("grettings", "Hello world Modele View");
        return "home";
    }
}
