package com.tomateunmate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(
            @RequestParam(required = false) String error,
            @RequestParam(required = false) String logout,
            HttpSession session,
            Model model) {
        
        if (error != null) {
            String errorMessage = (String) session.getAttribute("AUTH_ERROR");
            model.addAttribute("authError", errorMessage);
            session.removeAttribute("AUTH_ERROR");
        }
        
        if (logout != null) {
            model.addAttribute("logout", true);
        }
        
        return "login";
    }
}
