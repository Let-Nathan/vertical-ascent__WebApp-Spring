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
    
    /**
     * Handle log-in from user.
     * Defined by Spring security and implement in Security Config.
     *
     * @return view (If log-in successful redirect to "/account" path).
     */
    @GetMapping("/login")
    public final String loginForm() {
        return "login";
    }
}
