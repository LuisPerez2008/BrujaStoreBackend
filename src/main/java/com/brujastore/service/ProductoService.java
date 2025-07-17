package com.brujastore.service;

import com.brujastore.entity.Producto;
import com.brujastore.repository.DetalleCompraRepository;
import com.brujastore.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;



    @Transactional
    public Producto save(Producto producto, MultipartFile archivoImagen) throws IOException {
        // Asigna la imagen al producto convirtiendo el archivo a byte[]
        if (archivoImagen != null && !archivoImagen.isEmpty()) {
            producto.setImagen(archivoImagen.getBytes());
        }
        return productoRepository.save(producto);
    }

    @Transactional
    public Optional<Producto> update(Long id, Producto productoDetails, MultipartFile archivoImagen) throws IOException {
        return productoRepository.findById(id).map(productoExistente -> {
            productoExistente.setNombre(productoDetails.getNombre());
            productoExistente.setEstado(productoDetails.getEstado());
            productoExistente.setCaracteristicas(productoDetails.getCaracteristicas());
            productoExistente.setStock(productoDetails.getStock());
            productoExistente.setPrecioVenta(productoDetails.getPrecioVenta());
            productoExistente.setCategoria(productoDetails.getCategoria());

            if (archivoImagen != null && !archivoImagen.isEmpty()) {
                try {
                    productoExistente.setImagen(archivoImagen.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException("Error al leer el archivo de imagen", e);
                }
            }
            return productoRepository.save(productoExistente);
        });
    }

    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Producto> findById(Long id) {
        return productoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Producto> findByCategoriaId(Long categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId);
    }

    @Transactional(readOnly = true)
    public List<Producto> searchByNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Transactional
    public void deleteById(Long id) {
        productoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Producto> findProductosActivos() {
        // Llama al método del repositorio pasándole "true" como parámetro.
        return productoRepository.findByEstado(true);
    }

    @Transactional(readOnly = true)
    public Page<Producto> findAllPaginated(Pageable pageable) {
        return productoRepository.findAll(pageable);
    }


    @Transactional
    public Optional<Producto> actualizarEstado(Long id, boolean nuevoEstado) {
        return productoRepository.findById(id)
                .map(prodcutoExistente -> {
                    prodcutoExistente.setEstado(nuevoEstado);
                    return productoRepository.save(prodcutoExistente);
                });
    }




}