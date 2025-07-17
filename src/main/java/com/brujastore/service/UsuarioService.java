package com.brujastore.service;

import com.brujastore.entity.Usuario;
import com.brujastore.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // --- MÉTODOS CRUD EXISTENTES ---

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    public Usuario save(Usuario usuario) {

        usuario.setContra(passwordEncoder.encode(usuario.getContra()));
        return usuarioRepository.save(usuario);
    }

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Usuario> findByRolId(Long rolId) {
        return usuarioRepository.findByRolId(rolId);
    }

    // ---  MÉTODO PARA LOGIN ---

    public Optional<Usuario> validarCredenciales(String correo, String contra) {

        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);
        if (usuarioOpt.isEmpty()) {
            return Optional.empty();
        }
        Usuario usuario = usuarioOpt.get();

        if (passwordEncoder.matches(contra, usuario.getContra())) {
            return Optional.of(usuario);
        }
        return Optional.empty();
    }
}