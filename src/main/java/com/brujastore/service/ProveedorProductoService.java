package com.brujastore.service;

import com.brujastore.entity.ProveedorProducto;
import com.brujastore.repository.ProveedorProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorProductoService {

    @Autowired
    private ProveedorProductoRepository repository;

    @Transactional
    public ProveedorProducto save(ProveedorProducto proveedorProducto) {
        // Validamos que la combinación no exista ya
        Long proveedorId = proveedorProducto.getProveedor().getId();
        Long productoId = proveedorProducto.getProducto().getId();

        repository.findByProveedorIdAndProductoId(proveedorId, productoId).ifPresent(pp -> {
            throw new IllegalStateException("Esta relación entre proveedor y producto ya existe.");
        });

        return repository.save(proveedorProducto);
    }

    @Transactional
    public Optional<ProveedorProducto> update(Long id, ProveedorProducto details) {
        return repository.findById(id).map(existente -> {
            existente.setPrecioCompra(details.getPrecioCompra());
            // Normalmente, el proveedor y el producto no se cambian, solo el precio de compra.
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
    public List<ProveedorProducto> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<ProveedorProducto> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<ProveedorProducto> findByProveedorId(Long proveedorId) {
        return repository.findByProveedorId(proveedorId);
    }

    @Transactional(readOnly = true)
    public List<ProveedorProducto> findByProductoId(Long productoId) {
        return repository.findByProductoId(productoId);
    }
}