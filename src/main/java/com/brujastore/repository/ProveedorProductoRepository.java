package com.brujastore.repository;

import com.brujastore.entity.ProveedorProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProveedorProductoRepository extends JpaRepository<ProveedorProducto, Long> {


    List<ProveedorProducto> findByProveedorId(Long proveedorId);


    List<ProveedorProducto> findByProductoId(Long productoId);

    /**
     * Busca la entrada exacta para una combinación de proveedor y producto.
     * Útil para evitar duplicados.
     */
    Optional<ProveedorProducto> findByProveedorIdAndProductoId(Long proveedorId, Long productoId);
}