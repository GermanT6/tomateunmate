package com.tomateunmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tomateunmate.entitie.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    
}
