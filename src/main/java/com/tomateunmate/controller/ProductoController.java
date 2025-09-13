package com.tomateunmate.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tomateunmate.entitie.Producto;
import com.tomateunmate.service.ProductoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ProductoController {

    @Autowired 
    private ProductoService productoService;

    @GetMapping("/productos")
    public String productos(@RequestParam(required = false) String filtro, Model model) {
        List<Producto> productos;
        if (filtro != null && !filtro.isEmpty()) {
            productos = productoService.buscarPorNombre(filtro);
        } else {
            productos = productoService.obtenerTodos();
        }

        // Ordenar alfabéticamente por nombre
        productos.sort((a, b) -> a.getNombre().compareToIgnoreCase(b.getNombre()));

        double totalStockVenta = productos.stream()
            .mapToDouble(p -> p.getPrecioVenta() * p.getStock())
            .sum();

        double totalStockCompra = productos.stream()
            .mapToDouble(p -> p.getPrecioCompra() * p.getStock())
            .sum();

        model.addAttribute("totalStockCompra", totalStockCompra);
        model.addAttribute("totalStockVenta", totalStockVenta);
        model.addAttribute("productos", productos);
        model.addAttribute("producto", new Producto());
        return "productos";
    }

    @GetMapping("/editar-producto/{id}")
    public String editarProducto(@PathVariable Long id, Model model) {
        Producto producto = productoService.obtenerPorId(id);
        model.addAttribute("producto", producto);
        model.addAttribute("productos", productoService.obtenerTodos());
        return "productos";
    }

    @PostMapping("/guardar-producto")
    public String guardarProducto(@ModelAttribute Producto producto, RedirectAttributes redirectAttributes) {
        try {
            boolean isNew = producto.getId() == null;
            productoService.guardar(producto);
            
            if(isNew) {
                redirectAttributes.addFlashAttribute("success", "¡Producto creado exitosamente!");
            } else {
                redirectAttributes.addFlashAttribute("success", "¡Producto actualizado correctamente!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el producto: " + e.getMessage());
        }
        return "redirect:/productos";
    }
    
    @GetMapping("/eliminar-producto/{id}")
    public String eliminarProducto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productoService.eliminar(id);
            redirectAttributes.addFlashAttribute("success", "Producto eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el producto: " + e.getMessage());
        }
        return "redirect:/productos";
    }
}