package com.brujastore.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "devolucion")
public class Devolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // La línea de compra específica que se está devolviendo
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "detallecompra_id", nullable = false)
    private DetalleCompra detalleCompra;

    // El usuario que solicita la devolución
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "motivo", length = 255)
    private String motivo;

    @Column(name = "descripcion", length = 1000)
    private String descripcion;

    @Column(name = "estado", length = 50)
    private String estado; // Ej: "SOLICITADA", "APROBADA", "EN PROCESO", "RECHAZADA", "COMPLETADA"

    // La fecha de solicitud se asignará automáticamente al crear la entidad
    @CreationTimestamp
    @Column(name = "fecha_solicitud", nullable = false, updatable = false)
    private LocalDateTime fechaSolicitud;

    // La fecha de cierre será nula hasta que el proceso termine
    @Column(name = "fecha_cierre")
    private LocalDateTime fechaCierre;
}
