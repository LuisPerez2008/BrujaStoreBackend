package com.brujastore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "direccion")
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo", length = 50)
    private String tipo;

    @Column(name = "calle", nullable = false, length = 255)
    private String calle;

    @Column(name = "numero", length = 20)
    private String numero;

    @Column(name = "ciudad", nullable = false, length = 100)
    private String ciudad;

    @Column(name = "codigo_postal", length = 10)
    private String codigoPostal;

    @Column(name = "espredeterminado")
    private Boolean esPredeterminado;

    @Column(name = "estado")
    private Boolean estado;

    // Relación: Muchas direcciones pertenecen a un Usuario.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}
