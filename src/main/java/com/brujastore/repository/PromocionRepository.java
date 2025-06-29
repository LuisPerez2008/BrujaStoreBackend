package com.brujastore.repository;

import com.brujastore.entity.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {


    Optional<Promocion> findByCodigo(String codigo);


    List<Promocion> findByEstadoTrueAndFechaInicioBeforeAndFechaFinAfter(LocalDate fechaInicio, LocalDate fechaFin);
}
