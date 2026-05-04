package com.example.Sistema_Biblioteca.repository;

import com.example.Sistema_Biblioteca.entity.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlquilerRepository extends JpaRepository <Alquiler,Long> {

    List<Alquiler> findByUsuarioIdUsuario(Long idUsuario);
}
