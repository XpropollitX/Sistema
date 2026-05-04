package com.example.Sistema_Biblioteca.dto.response;

import com.example.Sistema_Biblioteca.enums.EstadoLibro;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO de respuesta para mostrar libros en catálogo y administración.
 */
@Getter
@Setter
@AllArgsConstructor
public class LibroResponse {

    private Long id;
    private String titulo;
    private String autor;
    private String isbn;
    private BigDecimal precioVenta;
    private BigDecimal precioAlquiler;
    private Integer stock;
    private String categoria;
    private String descripcion;
    private String urlPortada;
    private EstadoLibro estado;
    private Boolean activo;
}