package com.brujastore.service;

import com.brujastore.entity.Rol;
import com.brujastore.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
    public class RolService {

        @Autowired
        private RolRepository rolRepository;

        public List<Rol> findAll() {
            return rolRepository.findAll();
        }

        public Optional<Rol> findById(Long id) {
            return rolRepository.findById(id);
        }

        public Rol save(Rol rol) {
            return rolRepository.save(rol);
        }

        public void deleteById(Long id) {
            rolRepository.deleteById(id);
        }
    }

