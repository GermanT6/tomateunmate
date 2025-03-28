package com.tomateunmate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tomateunmate.entitie.DetalleCompra;

public interface DetalleCompraRepository extends JpaRepository<DetalleCompra, Long> {
    List<DetalleCompra> findByCompraId(Long compraId);

}
