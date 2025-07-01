package com.brujastore.controller;

import com.brujastore.entity.Devolucion;
import com.brujastore.service.DevolucionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/devoluciones")
public class DevolucionController {

    @Autowired
    private DevolucionService devolucionService;

    @PostMapping
    public ResponseEntity<Devolucion> crearDevolucion(@RequestBody Devolucion devolucion) {
        try {
            // El estado inicial debería ser "SOLICITADA"
            devolucion.setEstado("SOLICITADA");
            Devolucion nuevaDevolucion = devolucionService.save(devolucion);
            return new ResponseEntity<>(nuevaDevolucion, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint flexible para listar devoluciones.
     * Se puede filtrar por usuario, por estado, o traer todas.
     * Ej 1: /api/devoluciones?usuarioId=1  (historial de un usuario)
     * Ej 2: /api/devoluciones?estado=SOLICITADA (pendientes para un admin)
     * Ej 3: /api/devoluciones (todas)
     */
    @GetMapping
    public ResponseEntity<List<Devolucion>> listarDevoluciones(
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) String estado) {

        List<Devolucion> devoluciones;
        if (usuarioId != null) {
            devoluciones = devolucionService.findByUsuarioId(usuarioId);
        } else if (estado != null) {
            devoluciones = devolucionService.findByEstado(estado);
        } else {
            devoluciones = devolucionService.findAll();
        }
        return ResponseEntity.ok(devoluciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Devolucion> getDevolucionPorId(@PathVariable Long id) {
        return devolucionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Devolucion> actualizarDevolucion(@PathVariable Long id, @RequestBody Devolucion devolucionDetails) {
        return devolucionService.update(id, devolucionDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> eliminarDevolucion(@PathVariable Long id) {
        if (devolucionService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Devolucion> actualizarEstadoDevolucion(@PathVariable Long id, @RequestBody Map<String, String> updates) {
        // Obtenemos el nuevo estado del cuerpo del JSON
        String nuevoEstado = updates.get("estado");

        if (nuevoEstado == null) {
            return ResponseEntity.badRequest().build(); // Devuelve error si el JSON no tiene el campo 'estado'
        }

        return devolucionService.actualizarEstado(id, nuevoEstado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
