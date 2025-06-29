package com.brujastore.service;

import com.brujastore.entity.Direccion;
import com.brujastore.repository.DireccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DireccionService {

    @Autowired
    private DireccionRepository direccionRepository;



    @Transactional(readOnly = true)
    public List<Direccion> findAll() {
        return direccionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Direccion> findByUsuarioId(Long usuarioId) {
        return direccionRepository.findByUsuarioId(usuarioId);
    }

    @Transactional(readOnly = true)
    public Optional<Direccion> findById(Long id) {
        return direccionRepository.findById(id);
    }

    @Transactional
    public Direccion guardarDireccion(Direccion direccion) {
        return direccionRepository.save(direccion);
    }

    @Transactional
    public Optional<Direccion> actualizarDireccion(Long id, Direccion direccionDetails) {
        return direccionRepository.findById(id)
                .map(direccionExistente -> {
                    direccionExistente.setTipo(direccionDetails.getTipo());
                    direccionExistente.setCalle(direccionDetails.getCalle());
                    direccionExistente.setNumero(direccionDetails.getNumero());
                    direccionExistente.setCiudad(direccionDetails.getCiudad());
                    direccionExistente.setCodigoPostal(direccionDetails.getCodigoPostal());
                    direccionExistente.setEsPredeterminado(direccionDetails.getEsPredeterminado());
                    direccionExistente.setEstado(direccionDetails.getEstado());
                    return direccionRepository.save(direccionExistente);
                });
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (direccionRepository.existsById(id)) {
            direccionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}