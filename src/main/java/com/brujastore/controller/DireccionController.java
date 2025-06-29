package com.brujastore.controller;


import com.brujastore.entity.Direccion;
import com.brujastore.service.DireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/direcciones")
public class DireccionController {

    @Autowired
    private DireccionService direccionService;


    @GetMapping
    public ResponseEntity<List<Direccion>> listarDirecciones(@RequestParam(required = false) Long usuarioId) {
        List<Direccion> direcciones;
        if (usuarioId != null) {
            direcciones = direccionService.findByUsuarioId(usuarioId);
        } else {
            direcciones = direccionService.findAll(); // Necesitamos un método findAll en el servicio
        }
        return new ResponseEntity<>(direcciones, HttpStatus.OK);
    }

    /**
     * Obtiene una dirección específica por su ID.
     * Ejemplo: GET /api/direcciones/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<Direccion> obtenerDireccionPorId(@PathVariable Long id) {
        return direccionService.findById(id)
                .map(direccion -> new ResponseEntity<>(direccion, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public ResponseEntity<Direccion> crearDireccion(@RequestBody Direccion direccion) {
        try {
            Direccion nuevaDireccion = direccionService.guardarDireccion(direccion);
            return new ResponseEntity<>(nuevaDireccion, HttpStatus.CREATED);
        } catch (Exception e) {
            // Esto puede ocurrir si el usuarioId en el JSON no existe, por ejemplo.
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Actualiza una dirección existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Direccion> actualizarDireccion(@PathVariable Long id, @RequestBody Direccion direccionDetails) {
        return direccionService.actualizarDireccion(id, direccionDetails)
                .map(direccionActualizada -> new ResponseEntity<>(direccionActualizada, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Elimina una dirección por su ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> eliminarDireccion(@PathVariable Long id) {
        if (direccionService.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Direccion>> listarDireccionesPorUsuario(@PathVariable Long usuarioId) {
        // Ya no necesitas el 'if', porque el usuarioId siempre vendrá
        List<Direccion> direcciones = direccionService.findByUsuarioId(usuarioId);

        // Si no se encuentran direcciones, el servicio devolverá una lista vacía, lo cual es correcto.
        return new ResponseEntity<>(direcciones, HttpStatus.OK);
    }
}