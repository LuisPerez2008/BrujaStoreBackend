package com.brujastore.service;

import com.brujastore.entity.Proveedor;
import com.brujastore.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Transactional
    public Proveedor save(Proveedor proveedor) {
        // Validamos que el RUC no esté ya registrado
        proveedorRepository.findByRuc(proveedor.getRuc()).ifPresent(p -> {
            throw new IllegalStateException("Ya existe un proveedor con el RUC: " + proveedor.getRuc());
        });
        return proveedorRepository.save(proveedor);
    }

    @Transactional
    public Optional<Proveedor> update(Long id, Proveedor proveedorDetails) {
        return proveedorRepository.findById(id).map(proveedorExistente -> {
            proveedorExistente.setRuc(proveedorDetails.getRuc());
            proveedorExistente.setNombre(proveedorDetails.getNombre());
            proveedorExistente.setEstado(proveedorDetails.getEstado());
            return proveedorRepository.save(proveedorExistente);
        });
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (proveedorRepository.existsById(id)) {
            // Aquí se podría añadir lógica para no borrar si tiene productos/pedidos asociados
            proveedorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<Proveedor> findAll() {
        return proveedorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Proveedor> findById(Long id) {
        return proveedorRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Proveedor> findByRuc(String ruc) {
        return proveedorRepository.findByRuc(ruc);
    }

    @Transactional(readOnly = true)
    public List<Proveedor> findByEstado(boolean estado) {
        return proveedorRepository.findByEstado(estado);
    }
}

