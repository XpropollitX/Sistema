package com.example.Sistema_Biblioteca.dto.response;

import com.example.Sistema_Biblioteca.enums.EstadoVenta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO de respuesta completo para ventas.
 */
@Getter
@Setter
@AllArgsConstructor
public class VentaResponse {

    private Long ventaId;
    private EstadoVenta estado;
    private BigDecimal total;
    private LocalDateTime fecha;
    private ClienteResumenResponse cliente;
    private List<DetalleVentaResponse> detalle;
    private String resumen;
}