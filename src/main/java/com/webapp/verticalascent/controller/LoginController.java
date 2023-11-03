package com.webapp.verticalascent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class for Home path.
 *
 * @author Nathan L.
 * @version 1.0
 *
 */
@Controller
public class LoginController {
    
    
    @GetMapping("/login-user")
    public final String loginForm() {
        return "login";
    }
 
}
