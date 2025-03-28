package com.tomateunmate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tomateunmate.entitie.DetalleCompra;
import com.tomateunmate.entitie.Producto;
import com.tomateunmate.entitie.Compra;
import com.tomateunmate.repository.DetalleCompraRepository;
import com.tomateunmate.repository.ProductoRepository;
import com.tomateunmate.repository.CompraRepository;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private DetalleCompraRepository detalleCompraRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public List<Compra> obtenerTodos() {
        return compraRepository.findAll();
    }

    public Compra guardarCompra(Compra compra) {
        return compraRepository.save(compra);
    }
    
    public DetalleCompra guardarDetalleCompra(DetalleCompra detalleCompra) {
        return detalleCompraRepository.save(detalleCompra);
    }

    @Transactional
    public void eliminarCompra(Long id) {
        Compra compra = compraRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Compra no encontrada"));
        
        // Actualizar stock de productos
        for (DetalleCompra detalle : compra.getDetalles()) {
            Producto producto = detalle.getProducto();
            producto.setStock(producto.getStock() - detalle.getCantidad());
            productoRepository.save(producto);
        }
        
        // Esto eliminará automáticamente los detalles por el orphanRemoval
        compraRepository.delete(compra);
    }

    public List<DetalleCompra> obtenerDetallesPorCompraId(Long compraId) {
        return detalleCompraRepository.findByCompraId(compraId);
    }
    
    
}