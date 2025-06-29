package com.brujastore.service;

import com.brujastore.entity.Compra;
import com.brujastore.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

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
}
