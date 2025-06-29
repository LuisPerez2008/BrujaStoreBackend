package com.brujastore.repository;

import com.brujastore.entity.DetalleCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleCompraRepository extends JpaRepository<DetalleCompra, Long> {

    List<DetalleCompra> findByCompraId(Long compraId);
}