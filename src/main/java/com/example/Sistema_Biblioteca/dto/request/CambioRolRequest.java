package com.example.Sistema_Biblioteca.dto.request;

import com.example.Sistema_Biblioteca.enums.Rol;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para cambiar el rol de un usuario.
 */
@Getter
@Setter
public class CambioRolRequest {

    @NotNull(message = "El rol es obligatorio")
    private Rol rol;
}