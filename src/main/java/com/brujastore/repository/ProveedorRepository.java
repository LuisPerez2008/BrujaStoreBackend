package com.brujastore.repository;

import com.brujastore.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    /**
     * Busca un proveedor por su número de RUC.
     */
    Optional<Proveedor> findByRuc(String ruc);

    /**
     * Busca proveedores por su estado (activos o inactivos).
     */
    List<Proveedor> findByEstado(boolean estado);
}