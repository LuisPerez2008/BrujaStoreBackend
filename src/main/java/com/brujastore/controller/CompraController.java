package com.brujastore.controller;

import com.brujastore.dto.ReporteVentasDTO;
import com.brujastore.entity.Compra;
import com.brujastore.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @GetMapping
    public ResponseEntity<List<Compra>> listarTodasLasCompras() {
        List<Compra> compras = compraService.findAll();
        return ResponseEntity.ok(compras);
    }
    @PostMapping
    public ResponseEntity<Compra> crearCompra(@RequestBody Compra compra) {
        try {
            Compra nuevaCompra = compraService.save(compra);
            return new ResponseEntity<>(nuevaCompra, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Compra>> getComprasPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(compraService.findByUsuarioId(usuarioId));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Compra> getCompraPorId(@PathVariable Long id) {
        return compraService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Compra> actualizarCompra(@PathVariable Long id, @RequestBody Compra compraDetails) {
        return compraService.update(id, compraDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }




    //endponits para datos del dashboard
    @GetMapping("/stats/ventas-diarias")
    public ResponseEntity<List<ReporteVentasDTO>> getVentasDiarias() {
        return ResponseEntity.ok(compraService.getVentasDiariasUltimoMes());
    }


    @GetMapping("/stats/ventas-mensuales")
    public ResponseEntity<List<ReporteVentasDTO>> getVentasMensuales() {
        return ResponseEntity.ok(compraService.getVentasMensualesUltimoAno());
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Compra> cambiarEstado(@PathVariable Long id, @RequestBody Map<String, String> updates){

        String nuevoEstado = updates.get("estado");

        if (nuevoEstado == null) {
            return ResponseEntity.badRequest().build();
        }

        return compraService.actualizarEstado(id, nuevoEstado).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
