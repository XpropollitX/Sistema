package com.example.Sistema_Biblioteca.repository;

import com.example.Sistema_Biblioteca.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    List<Venta> findByUsuarioIdUsuario(Long idUsuario);
}
