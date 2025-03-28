package com.tomateunmate.entitie;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "producto") // Nombre exacto de la tabla en la base de datos
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id") 
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "precio_compra")
    private int precioCompra;

    @Column(name = "precio_venta")
    private int precioVenta;

    @Column(name = "stock")
    private int stock;

}
