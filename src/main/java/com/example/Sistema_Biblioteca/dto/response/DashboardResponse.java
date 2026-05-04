package com.example.Sistema_Biblioteca.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO de respuesta para métricas administrativas.
 */
@Getter
@Setter
@AllArgsConstructor
public class DashboardResponse {

    private Long ventasHoy;
    private BigDecimal ingresosHoy;
    private BigDecimal ingresosMes;
    private Long totalUsuariosActivos;
    private Long totalLibrosActivos;
    private List<TopLibroResponse> top5Libros;
    private List<VentaDiaResponse> ventasUltimos7Dias;
}