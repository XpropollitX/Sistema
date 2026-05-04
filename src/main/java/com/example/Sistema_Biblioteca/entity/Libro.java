package com.example.Sistema_Biblioteca.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "libro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLibro;

    @Column(unique = true, nullable = false)
    private String isbn;

    private String titulo;
    private String autor;
    private double precioVenta;
    private double precioAlquiler;
    private int stock;
    private String tipo;
}