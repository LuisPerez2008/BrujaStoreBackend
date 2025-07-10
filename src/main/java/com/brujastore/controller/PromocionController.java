package com.brujastore.controller;

import com.brujastore.entity.Promocion;
import com.brujastore.service.PromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promociones")
public class PromocionController {

    @Autowired
    private PromocionService promocionService;

    @GetMapping
    public ResponseEntity<List<Promocion>> listarPromociones() {
        return ResponseEntity.ok(promocionService.findAll());
    }


    @GetMapping("/activas")
    public ResponseEntity<List<Promocion>> listarPromocionesActivas() {
        return ResponseEntity.ok(promocionService.findPromocionesActivasHoy());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Promocion> getPromocionPorId(@PathVariable Long id) {
        return promocionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para validar un código de promoción.
     * El cliente ingresa "CYBER2025" y la API le dice si es válido.
     */
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Promocion> getPromocionPorCodigo(@PathVariable String codigo) {
        return promocionService.findByCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok().build());
    }

    @PostMapping
    public ResponseEntity<Promocion> crearPromocion(@RequestBody Promocion promocion) {
        // Validación para no crear códigos duplicados
        if (promocionService.findByCodigo(promocion.getCodigo()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict
        }
        return new ResponseEntity<>(promocionService.save(promocion), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Promocion> actualizarPromocion(@PathVariable Long id, @RequestBody Promocion promocionDetails) {
        return promocionService.update(id, promocionDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPromocion(@PathVariable Long id) {
        if (promocionService.findById(id).isPresent()) {
            promocionService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}