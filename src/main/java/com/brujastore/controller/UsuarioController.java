package com.brujastore.controller;

import com.brujastore.dto.LoginRequest;
import com.brujastore.entity.Usuario;
import com.brujastore.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return new ResponseEntity<>(usuarioService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/rol/{rolId}")
    public ResponseEntity<List<Usuario>> listarUsuariosPorRol(@PathVariable Long rolId) {
        List<Usuario> usuarios = usuarioService.findByRolId(rolId);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        return usuarioService.findById(id)
                .map(usuario -> new ResponseEntity<>(usuario, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {

        if (usuarioService.findByCorreo(usuario.getCorreo()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Usuario nuevoUsuario = usuarioService.save(usuario);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioDetalles) {
        return usuarioService.findById(id)
                .map(usuarioExistente -> {
                    usuarioExistente.setNombre(usuarioDetalles.getNombre());
                    usuarioExistente.setApellido(usuarioDetalles.getApellido());
                    usuarioExistente.setCorreo(usuarioDetalles.getCorreo());
                    // Lógica para actualizar contraseña si se proporciona una nueva
                    if (usuarioDetalles.getContra() != null && !usuarioDetalles.getContra().isEmpty()) {
                        usuarioExistente.setContra(usuarioDetalles.getContra());
                        // El servicio se encargará de codificarla al guardar
                    }
                    usuarioExistente.setRol(usuarioDetalles.getRol());

                    Usuario usuarioActualizado = usuarioService.save(usuarioExistente);
                    return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> eliminarUsuario(@PathVariable Long id) {
        try {
            usuarioService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody LoginRequest loginRequest) {
        Optional<Usuario> usuarioOpt = usuarioService.validarCredenciales(
                loginRequest.getCorreo(),
                loginRequest.getContra()
        );

        if (usuarioOpt.isPresent()) {


            return ResponseEntity.ok(usuarioOpt.get());
        } else {
            // Credenciales incorrectas.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }



}

