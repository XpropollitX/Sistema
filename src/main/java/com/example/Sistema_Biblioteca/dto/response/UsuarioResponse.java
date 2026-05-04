package com.example.Sistema_Biblioteca.dto.response;

import com.example.Sistema_Biblioteca.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO de respuesta para usuarios del sistema.
 */
@Getter
@Setter
@AllArgsConstructor
public class UsuarioResponse {

    private Long id;
    private String nombre;
    private String email;
    private Rol rol;
    private Boolean activo;
    private LocalDateTime creadoEn;
}