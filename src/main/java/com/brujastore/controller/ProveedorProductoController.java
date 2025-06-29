package com.brujastore.controller;

import com.brujastore.entity.ProveedorProducto;
import com.brujastore.service.ProveedorProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedor-productos")
public class ProveedorProductoController {

    @Autowired
    private ProveedorProductoService service;

    @PostMapping
    public ResponseEntity<?> crearRelacion(@RequestBody ProveedorProducto proveedorProducto) {
        try {
            ProveedorProducto nuevaRelacion = service.save(proveedorProducto);
            return new ResponseEntity<>(nuevaRelacion, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/proveedor/{proveedorId}")
    public ResponseEntity<List<ProveedorProducto>> obtenerPorProveedorId(@PathVariable Long proveedorId) {
        return ResponseEntity.ok(service.findByProveedorId(proveedorId));
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<ProveedorProducto>> obtenerPorProductoId(@PathVariable Long productoId) {
        return ResponseEntity.ok(service.findByProductoId(productoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorProducto> obtenerPorId(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorProducto> actualizarRelacion(@PathVariable Long id, @RequestBody ProveedorProducto details) {
        return service.update(id, details)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> eliminarRelacion(@PathVariable Long id) {
        if (service.deleteById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}