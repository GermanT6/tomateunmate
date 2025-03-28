package com.tomateunmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tomateunmate.entitie.Compra;

public interface CompraRepository extends JpaRepository<Compra, Long> {
    
}
