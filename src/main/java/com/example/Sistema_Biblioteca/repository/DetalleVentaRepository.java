package com.example.Sistema_Biblioteca.repository;

import com.example.Sistema_Biblioteca.entity.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleVentaRepository extends JpaRepository <DetalleVenta, Long> {
}
