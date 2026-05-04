package com.example.Sistema_Biblioteca.repository;

import com.example.Sistema_Biblioteca.entity.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {

    List<DetalleVenta> findByVentaIdVenta(Long idVenta);
}