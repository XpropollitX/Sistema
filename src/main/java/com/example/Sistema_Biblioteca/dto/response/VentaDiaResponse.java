package com.example.Sistema_Biblioteca.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para mostrar ventas agrupadas por día.
 */
@Getter
@Setter
@AllArgsConstructor
public class VentaDiaResponse {

    private LocalDate fecha;
    private Long totalVentas;
    private BigDecimal ingresos;
}