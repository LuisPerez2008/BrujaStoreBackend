package com.brujastore.repository;

import com.brujastore.dto.ReporteVentasDTO;
import com.brujastore.entity.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

    // Para obtener el historial de compras de un cliente, ordenado por fecha
    List<Compra> findByUsuarioIdOrderByFechaDesc(Long usuarioId);

    @Query("SELECT new com.brujastore.dto.ReporteVentasDTO(CAST(c.fecha AS LocalDate), SUM(c.precioFinal)) " +
            "FROM Compra c WHERE c.fecha BETWEEN :fechaInicio AND :fechaFin " +
            "GROUP BY CAST(c.fecha AS LocalDate) " +
            "ORDER BY CAST(c.fecha AS LocalDate) ASC")
    List<ReporteVentasDTO> findVentasDiarias(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);

    @Query("SELECT YEAR(c.fecha), MONTH(c.fecha), SUM(c.precioFinal) " +
            "FROM Compra c WHERE c.fecha BETWEEN :fechaInicio AND :fechaFin " +
            "GROUP BY YEAR(c.fecha), MONTH(c.fecha) " +
            "ORDER BY YEAR(c.fecha), MONTH(c.fecha) ASC")
    List<Object[]> findVentasMensuales(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
}
