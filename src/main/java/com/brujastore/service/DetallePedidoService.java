package com.brujastore.service;

import com.brujastore.entity.DetallePedido;
import com.brujastore.repository.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DetallePedidoService {

    @Autowired
    private DetallePedidoRepository repository;

    @Transactional
    public DetallePedido save(DetallePedido detallePedido) {
        return repository.save(detallePedido);
    }

    @Transactional
    public Optional<DetallePedido> update(Long id, DetallePedido details) {
        return repository.findById(id).map(existente -> {
            existente.setProveedorProducto(details.getProveedorProducto());
            existente.setCantidad(details.getCantidad());
            existente.setPrecioParcial(details.getPrecioParcial());
            return repository.save(existente);
        });
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public Optional<DetallePedido> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<DetallePedido> findByPedidoId(Long pedidoId) {
        return repository.findByPedidoId(pedidoId);
    }
}