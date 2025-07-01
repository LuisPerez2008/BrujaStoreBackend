package com.brujastore.service;

import com.brujastore.entity.DetalleCompra;
import com.brujastore.repository.DetalleCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleCompraService {

    @Autowired
    private DetalleCompraRepository detallesCompraRepository;


    @Transactional
    public DetalleCompra save(DetalleCompra detallesCompra) {
        return detallesCompraRepository.save(detallesCompra);
    }

    @Transactional
    public Optional<DetalleCompra> update(Long id, DetalleCompra detalleDetails) {
        return detallesCompraRepository.findById(id)
                .map(detalleExistente -> {
                    // Actualiza los campos que se pueden modificar
                    detalleExistente.setProducto(detalleDetails.getProducto());
                    detalleExistente.setCantidad(detalleDetails.getCantidad());
                    detalleExistente.setPrecioParcial(detalleDetails.getPrecioParcial());
                    return detallesCompraRepository.save(detalleExistente);
                });
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (detallesCompraRepository.existsById(id)) {
            detallesCompraRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // --- MÉTODOS DE LECTURA (READ) ---

    @Transactional(readOnly = true)
    public Optional<DetalleCompra> findById(Long id) {
        return detallesCompraRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<DetalleCompra> findByCompraId(Long compraId) {
        return detallesCompraRepository.findByCompraId(compraId);
    }
}
