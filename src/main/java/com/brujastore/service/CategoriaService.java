package com.brujastore.service;

import com.brujastore.entity.Categoria;
import com.brujastore.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;


    @Transactional(readOnly = true)
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Categoria> findById(Long id) {
        return categoriaRepository.findById(id);
    }

    @Transactional
    public Categoria save(Categoria categoria) {

        categoriaRepository.findByNombreIgnoreCase(categoria.getNombre()).ifPresent(c -> {
            throw new IllegalStateException("Ya existe una categoría con el nombre: " + categoria.getNombre());
        });
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Optional<Categoria> update(Long id, Categoria categoriaDetails) {
        return categoriaRepository.findById(id).map(categoriaExistente -> {
            categoriaExistente.setNombre(categoriaDetails.getNombre());
            return categoriaRepository.save(categoriaExistente);
        });
    }


}
