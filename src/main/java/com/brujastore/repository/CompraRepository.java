package com.brujastore.repository;

import com.brujastore.entity.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

    // Para obtener el historial de compras de un cliente, ordenado por fecha
    List<Compra> findByUsuarioIdOrderByFechaDesc(Long usuarioId);
}
