package com.example.Sistema_Biblioteca.repository;

import com.example.Sistema_Biblioteca.entity.Venta;
import com.example.Sistema_Biblioteca.enums.EstadoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    List<Venta> findByUsuarioIdUsuarioOrderByCreadoEnDesc(Long idUsuario);

    Optional<Venta> findByIdVentaAndUsuarioIdUsuario(Long idVenta, Long idUsuario);

    List<Venta> findByEstado(EstadoVenta estado);

    List<Venta> findByCreadoEnBetweenOrderByCreadoEnDesc(
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin
    );

    long countByCreadoEnBetween(
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin
    );

    @Query("""
            SELECT COALESCE(SUM(v.total), 0)
            FROM Venta v
            WHERE v.creadoEn BETWEEN :fechaInicio AND :fechaFin
            """)
    BigDecimal sumarIngresosEntreFechas(
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin
    );
}