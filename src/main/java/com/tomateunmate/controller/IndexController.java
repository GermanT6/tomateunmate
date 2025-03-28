package com.tomateunmate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.tomateunmate.entitie.Compra;
import com.tomateunmate.entitie.Venta;
import com.tomateunmate.service.CompraService;
import com.tomateunmate.service.VentaService;
import org.springframework.ui.Model;


@Controller
public class IndexController {


    @Autowired
    private VentaService ventaService;

    @Autowired
    private CompraService compraService;

    @GetMapping("/index")
    public String indexLog(Model model) {
        System.out.println("hola INDEX-LOG");

        List<Venta> venta;
        List<Compra> compra;

        venta = ventaService.obtenerTodos();
        compra = compraService.obtenerTodos();

        double totalVenta = venta.stream()
            .mapToDouble(p -> p.getTotal())
            .sum();

        double totalCompra = compra.stream()
            .mapToDouble(p -> p.getTotal())
            .sum();

        double balanceNeto = totalVenta - totalCompra;
        model.addAttribute("balanceNeto", balanceNeto);
        model.addAttribute("totalVenta", totalVenta);
        model.addAttribute("totalCompra", totalCompra);
        return "index"; // Página de login con datos
    }
}

