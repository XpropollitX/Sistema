package com.example.Sistema_Biblioteca.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table (name = "detalle_alquiler")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetalleAlquiler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalle;

    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_alquiler")
    private Alquiler alquiler;

    @ManyToOne
    @JoinColumn(name = "id_libro")
    private Libro libro;
}