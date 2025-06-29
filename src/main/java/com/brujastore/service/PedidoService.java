package com.brujastore.service;

import com.brujastore.entity.Pedido;
import com.brujastore.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Optional<Pedido> update(Long id, Pedido pedidoDetails) {
        return pedidoRepository.findById(id).map(existente -> {
            existente.setEstado(pedidoDetails.getEstado());
            existente.setPrecioTotal(pedidoDetails.getPrecioTotal());
            return pedidoRepository.save(existente);
        });
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }
}