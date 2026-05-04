package com.example.Sistema_Biblioteca.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DTO para crear una venta desde Postman.
 */
@Getter
@Setter
public class VentaRequest {

    @Valid
    @NotEmpty(message = "La venta debe tener al menos un item")
    private List<VentaItemRequest> items;
}