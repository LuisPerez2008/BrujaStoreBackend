package com.brujastore.service;

import com.brujastore.dto.ReporteVentasDTO;
import com.brujastore.entity.Compra;
import com.brujastore.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;


    // En CompraService.java

    @Transactional
    public Compra save(Compra compra) {
        // Simplemente guarda el objeto Compra que viene del controlador.
        return compraRepository.save(compra);
    }

    @Transactional(readOnly = true)
    public List<Compra> findAll() {
        return compraRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Compra> findById(Long id) {
        return compraRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Compra> findByUsuarioId(Long usuarioId) {
        return compraRepository.findByUsuarioIdOrderByFechaDesc(usuarioId);
    }

    @Transactional
    public void deleteById(Long id) {
        compraRepository.deleteById(id);
    }

    @Transactional
    public Optional<Compra> update(Long id, Compra compraDetails) {
        return compraRepository.findById(id)
                .map(compraExistente -> {
                    compraExistente.setUsuario(compraDetails.getUsuario());
                    compraExistente.setPromocion(compraDetails.getPromocion());
                    compraExistente.setPrecioFinal(compraDetails.getPrecioFinal());
                    compraExistente.setEstado(compraDetails.getEstado());
                    return compraRepository.save(compraExistente);
                });
    }

    @Transactional(readOnly = true)
    public List<ReporteVentasDTO> getVentasDiariasUltimoMes() {
        LocalDateTime fechaFin = LocalDateTime.now();
        LocalDateTime fechaInicio = fechaFin.minusDays(30);
        return compraRepository.findVentasDiarias(fechaInicio, fechaFin);
    }


    @Transactional(readOnly = true)
    public List<ReporteVentasDTO> getVentasMensualesUltimoAno() {
        LocalDateTime fechaFin = LocalDateTime.now();
        LocalDateTime fechaInicio = fechaFin.minusYears(1).with(TemporalAdjusters.firstDayOfMonth());

        // 1. Obtenemos la lista de arrays de objetos del repositorio
        List<Object[]> resultados = compraRepository.findVentasMensuales(fechaInicio, fechaFin);

        // 2. Creamos la lista que vamos a devolver
        List<ReporteVentasDTO> reporte = new ArrayList<>();

        // 3. Recorremos los resultados y los convertimos a DTOs
        for (Object[] resultado : resultados) {
            Integer anio = (Integer) resultado[0];
            Integer mes = (Integer) resultado[1];
            Double totalVentas = (Double) resultado[2];

            // Formateamos el mes para que tenga un cero si es necesario (ej. 7 -> "07")
            String mesFormateado = String.format("%02d", mes);
            String periodo = anio + "-" + mesFormateado;

            // Creamos el DTO y lo añadimos a la lista
            reporte.add(new ReporteVentasDTO(periodo, totalVentas));
        }

        return reporte;
    }
}
