package com.brujastore.repository;

import com.brujastore.entity.Devolucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DevolucionRepository extends JpaRepository<Devolucion, Long> {

    // Para que un usuario vea su historial de devoluciones
    List<Devolucion> findByUsuarioIdOrderByFechaSolicitudDesc(Long usuarioId);

    // Para que un administrador vea las devoluciones por estado (ej: todas las "SOLICITADA")
    List<Devolucion> findByEstadoOrderByFechaSolicitudAsc(String estado);
}