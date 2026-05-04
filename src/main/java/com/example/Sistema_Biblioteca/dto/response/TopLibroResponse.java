package com.example.Sistema_Biblioteca.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para mostrar los libros más vendidos en el dashboard.
 */
@Getter
@Setter
@AllArgsConstructor
public class TopLibroResponse {

    private String titulo;
    private String autor;
    private Long cantidadVendida;
}