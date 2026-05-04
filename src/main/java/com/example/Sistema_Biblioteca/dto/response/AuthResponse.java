package com.example.Sistema_Biblioteca.dto.response;

import com.example.Sistema_Biblioteca.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO de respuesta para login y registro.
 */
@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private Long userId;
    private String nombre;
    private String email;
    private Rol rol;
}