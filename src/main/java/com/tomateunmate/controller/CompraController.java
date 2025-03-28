package com.tomateunmate.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tomateunmate.entitie.Proveedor;
import com.tomateunmate.entitie.DetalleCompra;
import com.tomateunmate.entitie.Producto;
import com.tomateunmate.entitie.Compra;
import com.tomateunmate.service.ProveedorService;
import com.tomateunmate.service.ProductoService;
import com.tomateunmate.service.CompraService;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CompraController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private CompraService compraService;

    @GetMapping("/compras")
    public String compras(Model model) {
        List<Compra> compras = compraService.obtenerTodos();
        List<Producto> productos = productoService.obtenerTodos();
        List<Proveedor> proveedores = proveedorService.obtenerTodos();

        model.addAttribute("compras", compras);
        model.addAttribute("productos", productos);
        model.addAttribute("proveedores", proveedores);
        
        return "compras";
    }

    @PostMapping("/guardar-compra")
    public String guardarCompra(
        @RequestParam Date fecha,
        @RequestParam Long proveedorId,
        @RequestParam List<Long> productoIds,
        @RequestParam List<Integer> cantidades,
        @RequestParam List<Double> preciosUnitarios,
        RedirectAttributes redirectAttributes) {

        try {
            // Crear y guardar la compra principal
            Compra compra = new Compra();
            compra.setFecha(fecha);
            compra.setProveedor(proveedorService.obtenerPorId(proveedorId));
            
            // Calcular total
            double total = 0;
            for (int i = 0; i < productoIds.size(); i++) {
                total += preciosUnitarios.get(i) * cantidades.get(i);
            }
            compra.setTotal(total);
            
            Compra compraGuardada = compraService.guardarCompra(compra);
            
            // Guardar detalles de compra
            for (int i = 0; i < productoIds.size(); i++) {
                DetalleCompra detalle = new DetalleCompra();
                detalle.setCompra(compraGuardada);
                detalle.setProducto(productoService.obtenerPorId(productoIds.get(i)));
                detalle.setCantidad(cantidades.get(i));
                detalle.setPrecioUnitario(preciosUnitarios.get(i));
                
                compraService.guardarDetalleCompra(detalle);
                
                // Actualizar stock del producto (aumentar stock en compras)
                Producto producto = productoService.obtenerPorId(productoIds.get(i));
                producto.setStock(producto.getStock() + cantidades.get(i));
                productoService.guardar(producto);
            }
            
            redirectAttributes.addFlashAttribute("success", "¡Compra registrada exitosamente!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar la compra: " + e.getMessage());
        }
        
        return "redirect:/compras";
    }

    @GetMapping("/eliminar-compra/{id}")
    public String eliminarCompra(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            compraService.eliminarCompra(id);
            redirectAttributes.addFlashAttribute("success", "¡Compra eliminada correctamente!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la compra: " + e.getMessage());
        }
        return "redirect:/compras";
    }
}