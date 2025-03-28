package com.tomateunmate.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tomateunmate.entitie.Cliente;
import com.tomateunmate.entitie.DetalleVenta;
import com.tomateunmate.entitie.Producto;
import com.tomateunmate.entitie.Venta;
import com.tomateunmate.service.ClienteService;
import com.tomateunmate.service.ProductoService;
import com.tomateunmate.service.VentaService;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class VentaController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private VentaService ventaService;

    @GetMapping("/ventas")
    public String ventas(Model model) {

    List<Venta> ventas = ventaService.obtenerTodos();
    List<Producto> productos = productoService.obtenerTodos();
    List<Cliente> clientes = clienteService.obtenerTodos();

    model.addAttribute("ventas", ventas);
    model.addAttribute("productos", productos);
    model.addAttribute("clientes", clientes);
    
        return "ventas"; // Renderiza la página login.html
    }

    @PostMapping("/guardar-venta")
    public String guardarVenta(
        @RequestParam Date fecha,
        @RequestParam Long clienteId,
        @RequestParam List<Long> productoIds,
        @RequestParam List<Integer> cantidades,
        @RequestParam List<Double> preciosUnitarios,
        RedirectAttributes redirectAttributes) {

        try {
            // Crear y guardar la venta principal
            Venta venta = new Venta();
            venta.setFecha(fecha);
            venta.setCliente(clienteService.obtenerPorId(clienteId));
            
            // Calcular total
            double total = 0;
            for (int i = 0; i < productoIds.size(); i++) {
                total += preciosUnitarios.get(i) * cantidades.get(i);
            }
            venta.setTotal(total);
            
            Venta ventaGuardada = ventaService.guardarVenta(venta);
            
            // Guardar detalles de venta
            for (int i = 0; i < productoIds.size(); i++) {
                DetalleVenta detalle = new DetalleVenta();
                detalle.setVenta(ventaGuardada);
                detalle.setProducto(productoService.obtenerPorId(productoIds.get(i)));
                detalle.setCantidad(cantidades.get(i));
                detalle.setPrecioUnitario(preciosUnitarios.get(i));
                
                ventaService.guardarDetalleVenta(detalle);
                
                // Actualizar stock del producto
                Producto producto = productoService.obtenerPorId(productoIds.get(i));
                producto.setStock(producto.getStock() - cantidades.get(i));
                productoService.guardar(producto);
            }
            
            redirectAttributes.addFlashAttribute("success", "¡Venta registrada exitosamente!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar la venta: " + e.getMessage());
        }
        
        return "redirect:/ventas";
    }

    @GetMapping("/eliminar-venta/{id}")
        public String eliminarVenta(@PathVariable Long id, RedirectAttributes redirectAttributes) {
            try {
                ventaService.eliminarVenta(id);
                redirectAttributes.addFlashAttribute("success", "¡Venta eliminada correctamente!");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "Error al eliminar la venta: " + e.getMessage());
            }
            return "redirect:/ventas";
        }
    
}
