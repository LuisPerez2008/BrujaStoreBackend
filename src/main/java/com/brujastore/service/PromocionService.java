package com.brujastore.service;

import com.brujastore.entity.Promocion;
import com.brujastore.repository.PromocionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PromocionService {

    @Autowired
    private PromocionRepository promocionRepository;

    @Transactional(readOnly = true)
    public List<Promocion> findAll() {
        return promocionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Promocion> findById(Long id) {
        return promocionRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Promocion> findByCodigo(String codigo) {
        return promocionRepository.findByCodigo(codigo);
    }

    @Transactional(readOnly = true)
    public List<Promocion> findPromocionesActivasHoy() {
        LocalDate hoy = LocalDate.now();
        // El método busca promociones activas cuya fecha de inicio sea hoy o antes,
        // y cuya fecha de fin sea hoy o después.
        return promocionRepository.findByEstadoTrueAndFechaInicioBeforeAndFechaFinAfter(hoy.plusDays(1), hoy.minusDays(1));
    }

    @Transactional
    public Promocion save(Promocion promocion) {
        // Podríamos añadir una validación para no permitir guardar un código que ya existe
        return promocionRepository.save(promocion);
    }

    @Transactional
    public void deleteById(Long id) {
        promocionRepository.deleteById(id);
    }

    @Transactional
    public Optional<Promocion> update(Long id, Promocion promocionDetails) {
        return promocionRepository.findById(id)
                .map(promocionExistente -> {
                    promocionExistente.setCodigo(promocionDetails.getCodigo());
                    promocionExistente.setEstado(promocionDetails.getEstado());
                    promocionExistente.setFechaInicio(promocionDetails.getFechaInicio());
                    promocionExistente.setFechaFin(promocionDetails.getFechaFin());
                    promocionExistente.setDescuento(promocionDetails.getDescuento());
                    promocionExistente.setDetalle(promocionDetails.getDetalle());
                    return promocionRepository.save(promocionExistente);
                });
    }
}