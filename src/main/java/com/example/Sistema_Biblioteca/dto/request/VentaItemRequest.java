package com.example.Sistema_Biblioteca.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa un libro dentro de una venta.
 */
@Getter
@Setter
public class VentaItemRequest {

    @NotNull(message = "El id del libro es obligatorio")
    private Long libroId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;
}