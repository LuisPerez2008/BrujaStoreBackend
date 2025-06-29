package com.brujastore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "fecha", updatable = false)
    private LocalDateTime fecha;

    @Column(name = "estado", length = 50)
    private String estado;

    @Column(name = "preciototal", nullable = false)
    private Double precioTotal;

     /*// La relación sigue existiendo, pero la ignoramos en el JSON para evitar problemas
    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<DetallePedido> detallesPedido;
   */
}
