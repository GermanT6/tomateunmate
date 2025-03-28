package com.tomateunmate.service;


import com.tomateunmate.entitie.Proveedor;
import com.tomateunmate.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;
    
    public List<Proveedor> obtenerTodos() {
        return proveedorRepository.findAll();
    }
    
    public Proveedor guardar(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }
    
    public void eliminar(Long id) {
        proveedorRepository.deleteById(id);
    }

    public Proveedor obtenerPorId(Long id) {
        return proveedorRepository.findById(id).orElse(null);
    }

}