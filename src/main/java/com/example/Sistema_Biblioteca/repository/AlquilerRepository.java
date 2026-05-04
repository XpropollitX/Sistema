package com.example.Sistema_Biblioteca.repository;

import com.example.Sistema_Biblioteca.entity.Alquiler;
import com.example.Sistema_Biblioteca.enums.EstadoAlquiler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlquilerRepository extends JpaRepository<Alquiler, Long> {

    List<Alquiler> findByUsuarioIdUsuarioOrderByCreadoEnDesc(Long idUsuario);

    List<Alquiler> findByEstado(EstadoAlquiler estado);
}