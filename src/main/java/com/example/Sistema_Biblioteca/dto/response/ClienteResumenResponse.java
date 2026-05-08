package com.example.Sistema_Biblioteca.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO con datos básicos del cliente dentro de una venta.
 */
@Getter
@Setter
@AllArgsConstructor
public class ClienteResumenResponse {

    private Long idUsuario;
    private String nombre;
    private String email;
}