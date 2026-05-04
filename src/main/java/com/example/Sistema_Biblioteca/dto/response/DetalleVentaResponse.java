package com.example.Sistema_Biblioteca.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO de respuesta para cada item vendido.
 */
@Getter
@Setter
@AllArgsConstructor
public class DetalleVentaResponse {

    private Long libroId;
    private String titulo;
    private String autor;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}