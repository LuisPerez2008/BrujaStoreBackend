package com.brujastore.service;

import com.brujastore.entity.Puntos;
import com.brujastore.repository.PuntosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PuntosService {

    @Autowired
    private PuntosRepository puntosRepository;

    @Transactional
    public Puntos guardarPuntos(Puntos puntos) {
        return puntosRepository.save(puntos);
    }

    @Transactional(readOnly = true)
    public List<Puntos> findByUsuarioId(Long usuarioId) {
        return puntosRepository.findByUsuarioId(usuarioId);
    }

    @Transactional(readOnly = true)
    public Optional<Puntos> findById(Long id) {
        return puntosRepository.findById(id);
    }
}