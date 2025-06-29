package com.brujastore.repository;

import com.brujastore.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // No se necesitan métodos personalizados para este CRUD simple.
}
