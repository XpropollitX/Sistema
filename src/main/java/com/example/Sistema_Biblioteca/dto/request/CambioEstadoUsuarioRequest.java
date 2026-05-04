package com.example.Sistema_Biblioteca.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para activar o desactivar usuarios.
 */
@Getter
@Setter
public class CambioEstadoUsuarioRequest {

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo;
}