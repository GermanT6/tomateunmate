package com.tomateunmate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tomateunmate.entitie.DetalleVenta;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
    List<DetalleVenta> findByVentaId(Long ventaId);
    
}
