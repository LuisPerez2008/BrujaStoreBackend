package com.brujastore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "compra")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "direccion_id", nullable = false)
    private Direccion direccion;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promocion_id")
    private Promocion promocion;


    @CreationTimestamp
    @Column(name = "fecha", updatable = false)
    private LocalDateTime fecha;


    @Column(name = "preciofinal", nullable = false)
    private Double precioFinal;

    @Column(name = "estado", length = 50)
    private String estado;
}