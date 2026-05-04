package com.example.Sistema_Biblioteca.dto.request;

import com.example.Sistema_Biblioteca.enums.EstadoLibro;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para cambiar el estado de un libro.
 */
@Getter
@Setter
public class CambioEstadoRequest {

    @NotNull(message = "El estado es obligatorio")
    private EstadoLibro estado;
}