package com.brujastore.service;

import com.brujastore.entity.Devolucion;
import com.brujastore.repository.DevolucionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DevolucionService {

    @Autowired
    private DevolucionRepository devolucionRepository;

    @Transactional
    public Devolucion save(Devolucion devolucion) {
        return devolucionRepository.save(devolucion);
    }

    @Transactional
    public Optional<Devolucion> update(Long id, Devolucion devolucionDetails) {
        return devolucionRepository.findById(id)
                .map(devolucionExistente -> {
                    devolucionExistente.setMotivo(devolucionDetails.getMotivo());
                    devolucionExistente.setDescripcion(devolucionDetails.getDescripcion());
                    devolucionExistente.setEstado(devolucionDetails.getEstado());
                    // Si el estado cambia a "COMPLETADA" o "RECHAZADA", se podría setear la fecha de cierre
                    if ("COMPLETADA".equalsIgnoreCase(devolucionDetails.getEstado()) || "RECHAZADA".equalsIgnoreCase(devolucionDetails.getEstado())) {
                        devolucionExistente.setFechaCierre(LocalDateTime.now());
                    }
                    return devolucionRepository.save(devolucionExistente);
                });
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (devolucionRepository.existsById(id)) {
            devolucionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<Devolucion> findAll() {
        return devolucionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Devolucion> findById(Long id) {
        return devolucionRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Devolucion> findByUsuarioId(Long usuarioId) {
        return devolucionRepository.findByUsuarioIdOrderByFechaSolicitudDesc(usuarioId);
    }

    @Transactional(readOnly = true)
    public List<Devolucion> findByEstado(String estado) {
        return devolucionRepository.findByEstadoOrderByFechaSolicitudAsc(estado);
    }
}
