package com.tomateunmate.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.stereotype.Controller;

@Controller
public class RecuperarController {

    @GetMapping("/recuperar")
    public String recuperar(Model model) {
    
        return "recuperar"; // Renderiza la página recuperar.html
    }
    
}
