package com.tomateunmate.repository;

import com.tomateunmate.entitie.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}