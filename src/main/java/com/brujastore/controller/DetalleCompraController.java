package com.brujastore.controller;

import com.brujastore.entity.DetalleCompra;
import com.brujastore.service.DetalleCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalles-compra")
public class DetalleCompraController {

    @Autowired
    private DetalleCompraService detallesCompraService;



    @PostMapping
    public ResponseEntity<DetalleCompra> crearDetalle(@RequestBody DetalleCompra detallesCompra) {
        try {
            DetalleCompra nuevoDetalle = detallesCompraService.save(detallesCompra);
            return new ResponseEntity<>(nuevoDetalle, HttpStatus.CREATED);
        } catch (Exception e) {
            // Captura errores como IDs de producto o compra que no existen.
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleCompra> actualizarDetalle(@PathVariable Long id, @RequestBody DetalleCompra detalleDetails) {
        return detallesCompraService.update(id, detalleDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> eliminarDetalle(@PathVariable Long id) {
        if (detallesCompraService.deleteById(id)) {
            return ResponseEntity.noContent().build(); // Éxito, sin contenido que devolver
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // --- ENDPOINTS DE LECTURA ---

    @GetMapping("/{id}")
    public ResponseEntity<DetalleCompra> getDetallePorId(@PathVariable Long id) {
        return detallesCompraService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/compra/{compraId}")
    public ResponseEntity<List<DetalleCompra>> getDetallesPorCompraId(@PathVariable Long compraId) {
        List<DetalleCompra> detalles = detallesCompraService.findByCompraId(compraId);
        return ResponseEntity.ok(detalles);
    }
}
