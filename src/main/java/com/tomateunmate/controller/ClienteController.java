package com.tomateunmate.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.tomateunmate.entitie.Cliente;
import com.tomateunmate.service.ClienteService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/clientes")
    public String clientes(Model model) {

        List<Cliente> cliente;
        cliente = clienteService.obtenerTodos();

        cliente.sort((a, b) -> a.getId().compareTo(b.getId()));
        model.addAttribute("clientes", cliente);

        return "clientes"; // Renderiza la página login.html
    }

    @GetMapping("/editar-cliente/{id}")
    public String editarCliente(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.obtenerPorId(id);
        model.addAttribute("cliente", cliente);
        model.addAttribute("clientes", clienteService.obtenerTodos());
        return "clientes";
    }

    @PostMapping("/guardar-cliente")
    public String guardarCliente(@ModelAttribute Cliente cliente) {
        clienteService.guardar(cliente);
        return "redirect:/clientes";
    }
    
    @GetMapping("/eliminar-cliente/{id}")
    public String eliminarCliente(@PathVariable Long id) {
        clienteService.eliminar(id);
        return "redirect:/clientes";
    }
}
