package com.brujastore.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "caracteristicas", length = 1000)
    private String caracteristicas;

    @Column(name = "marca", length = 255)
    private String marca;


    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] imagen;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "precioventa")
    private Double precioVenta;

    // Relación: Muchos productos pertenecen a una Categoría.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;


}
