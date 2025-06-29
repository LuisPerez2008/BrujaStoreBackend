package com.brujastore.repository;

import com.brujastore.entity.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {

    /**
     * Busca todas las líneas de detalle para un ID de pedido específico.
     */
    List<DetallePedido> findByPedidoId(Long pedidoId);
}
