package com.brujastore.repository;

import com.brujastore.entity.Puntos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PuntosRepository extends JpaRepository<Puntos, Long> {


    List<Puntos> findByUsuarioId(Long usuarioId);
}
