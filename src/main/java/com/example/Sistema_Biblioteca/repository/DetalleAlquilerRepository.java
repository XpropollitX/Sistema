package com.example.Sistema_Biblioteca.repository;

import com.example.Sistema_Biblioteca.entity.DetalleAlquiler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleAlquilerRepository extends JpaRepository<DetalleAlquiler, Long> {

    List<DetalleAlquiler> findByAlquilerIdAlquiler(Long idAlquiler);
}