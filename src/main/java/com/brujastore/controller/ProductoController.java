package com.brujastore.controller;

import com.brujastore.entity.Producto;
import com.brujastore.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    /**
     * Lista todos los productos o busca por nombre.
     * Ej: GET /api/productos
     * Ej: GET /api/productos?nombre=camisa
     */
    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos(@RequestParam(required = false) String nombre) {
        List<Producto> productos;
        if (nombre != null && !nombre.isEmpty()) {
            productos = productoService.searchByNombre(nombre);
        } else {
            productos = productoService.findAll();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoPorId(@PathVariable Long id) {
        return productoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint especial para obtener solo la imagen de un producto.
     * Es más eficiente que enviar todo el objeto producto.
     */
    @GetMapping("/{id}/imagen")
    public ResponseEntity<byte[]> getImagenProducto(@PathVariable Long id) {
        return productoService.findById(id)
                .map(producto -> ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // O el tipo de imagen que uses
                        .body(producto.getImagen()))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo producto. Recibe los datos del producto como JSON
     * y el archivo de la imagen en una petición multipart/form-data.
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Producto> crearProducto(
            @RequestPart("producto") Producto producto,
            @RequestPart(value = "imagen", required = false) MultipartFile imagenFile) {
        try {
            Producto nuevoProducto = productoService.save(producto, imagenFile);
            return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Actualiza un producto existente.
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable Long id,
            @RequestPart("producto") Producto productoDetails,
            @RequestPart(value = "imagen", required = false) MultipartFile imagenFile) {
        try {
            return productoService.update(id, productoDetails, imagenFile)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/activos")
    public ResponseEntity<List<Producto>> listarProductosActivos() {
        List<Producto> productosActivos = productoService.findProductosActivos();
        return ResponseEntity.ok(productosActivos);
    }

    @GetMapping("/paginacion")
    public ResponseEntity<Page<Producto>> listarPaginacion(Pageable pageable) {
        Page<Producto> productos = productoService.findAllPaginated(pageable);

        return ResponseEntity.ok(productos);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Producto> cambiarEstado(@PathVariable Long id, @RequestBody Map<String, Boolean> updates){

        Boolean nuevoEstado = updates.get("estado");

        if (nuevoEstado == null) {
            return ResponseEntity.badRequest().build();
        }

        return productoService.actualizarEstado(id, nuevoEstado).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


}