package com.tomateunmate.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.tomateunmate.entitie.Proveedor;
import com.tomateunmate.service.ProveedorService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;
    
    @GetMapping("/proveedores")
    public String proveedores(Model model) {

        List<Proveedor> proveedor;
        
        proveedor = proveedorService.obtenerTodos();

        proveedor.sort((a, b) -> a.getId().compareTo(b.getId()));

        model.addAttribute("proveedores", proveedor);
    
        return "proveedores"; // Renderiza la página login.html
    }

    @GetMapping("/editar-proveedor/{id}")
    public String editarProveedor(@PathVariable Long id, Model model) {
        Proveedor proveedor = proveedorService.obtenerPorId(id);
        model.addAttribute("proveedor", proveedor);
        model.addAttribute("proveedores", proveedorService.obtenerTodos());
        return "proveedores";
    }

    @PostMapping("/guardar-proveedor")
    public String guardarProveedor(@ModelAttribute Proveedor proveedor) {
        proveedorService.guardar(proveedor);
        return "redirect:/proveedores";
    }
    
    @GetMapping("/eliminar-proveedor/{id}")
    public String eliminarProveedor(@PathVariable Long id) {
        proveedorService.eliminar(id);
        return "redirect:/proveedores";
    }

}
