package com.brujastore.controller;

import com.brujastore.entity.Puntos;
import com.brujastore.service.PuntosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/puntos")
public class PuntosController {

    @Autowired
    private PuntosService puntosService;

    /**
     * Crea una nueva transacción de puntos para un usuario.
     */
    @PostMapping
    public ResponseEntity<Puntos> crearPuntos(@RequestBody Puntos puntos) {
        try {
            Puntos nuevosPuntos = puntosService.guardarPuntos(puntos);
            return new ResponseEntity<>(nuevosPuntos, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtiene el historial de transacciones de puntos de un usuario.
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Puntos>> getHistorialPuntosPorUsuario(@PathVariable Long usuarioId) {
        List<Puntos> historial = puntosService.findByUsuarioId(usuarioId);
        return ResponseEntity.ok(historial);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Puntos> getPuntosPorId(@PathVariable Long id) {
        return puntosService.findById(id)
                .map(puntos -> new ResponseEntity<>(puntos, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}