package com.tomateunmate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tomateunmate.entitie.DetalleVenta;
import com.tomateunmate.entitie.Producto;
import com.tomateunmate.entitie.Venta;
import com.tomateunmate.repository.DetalleVentaRepository;
import com.tomateunmate.repository.ProductoRepository;
import com.tomateunmate.repository.VentaRepository;

@Service
public class VentaService {

 

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private ProductoRepository productoRepository;
 
    public List<Venta> obtenerTodos() {
        return ventaRepository.findAll();
    }

    public Venta guardarVenta(Venta venta) {
        return ventaRepository.save(venta);
    }
    
    public DetalleVenta guardarDetalleVenta(DetalleVenta detalleVenta) {
        return detalleVentaRepository.save(detalleVenta);
    }

    @Transactional
    public void eliminarVenta(Long id) {
        // 1. Obtener la venta
        Venta venta = ventaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        
        // 2. Obtener los detalles de venta asociados
        List<DetalleVenta> detalles = detalleVentaRepository.findByVentaId(venta.getId());
        
        // 3. Revertir el stock de los productos
        for (DetalleVenta detalle : detalles) {
            Producto producto = productoRepository.findById(detalle.getProducto().getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            
            producto.setStock(producto.getStock() + detalle.getCantidad());
            productoRepository.save(producto);
        }
        
        // 4. Primero eliminar los detalles (si no se hace en cascada)
        detalleVentaRepository.deleteAll(detalles);
        
        // 5. Finalmente eliminar la venta
        ventaRepository.delete(venta);
    }

    
    
}
