package com.tomateunmate.controller;

import com.tomateunmate.entitie.Usuario;
import com.tomateunmate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
public class RegistroController {

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registro")
    public String registro(Model model) {
    
        return "registro"; // Renderiza la página login.html
    }

    @PostMapping("/registrar-usuario")
    public String registerUser(@RequestParam String nombre,  
                                @RequestParam String email, 
                                @RequestParam String password1,
                                @RequestParam String password2,
                                @RequestParam String rol,
                                Model model) {
        

        if (!password1.equals(password2)) {
            model.addAttribute("authError", "Las contraseñas no coinciden.");
            // Agregar los valores de los campos al modelo para que no se pierdan
            model.addAttribute("nombre", nombre);
            model.addAttribute("email", email);
            model.addAttribute("rol", rol);
            return "registro"; // Retorna a la vista del formulario
        }


        // Verificar si ya está en uso el mail
        if (userService.findByEmail(email) != null) {
            model.addAttribute("authError", "El mail ya está en uso.");
            // Agregar los valores de los campos al modelo para que no se pierdan
            model.addAttribute("nombre", nombre);
            model.addAttribute("email", email);
            model.addAttribute("rol", rol);

            return "registro"; // Retorna a la vista del formulario
        }
    

        Usuario newUser = new Usuario();
        newUser.setEmail(email);
        newUser.setContraseña(passwordEncoder.encode(password1)); 
        newUser.setNombre(nombre);
        newUser.setRol(rol);
        userService.save(newUser);
    
        model.addAttribute("registro", "¡Te acabas de registrar! Ingresá tus datos.");
        return "/login";
    }
    

}