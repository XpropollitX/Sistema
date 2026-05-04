package com.example.Sistema_Biblioteca.entity;

import com.example.Sistema_Biblioteca.enums.EstadoLibro;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad que representa los libros disponibles para venta o alquiler.
 */
@Entity
@Table(name = "libros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro")
    private Long idLibro;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, length = 150)
    private String autor;

    @Column(nullable = false, unique = true, length = 20)
    private String isbn;

    @Column(name = "precio_venta", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioVenta;

    @Column(name = "precio_alquiler", precision = 10, scale = 2)
    private BigDecimal precioAlquiler;

    @Column(nullable = false)
    private Integer stock;

    @Column(length = 100)
    private String categoria;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "url_portada", length = 500)
    private String urlPortada;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EstadoLibro estado;

    @Column(nullable = false)
    private Boolean activo;

    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn;

    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

    @PrePersist
    public void prePersist() {
        this.activo = true;
        this.creadoEn = LocalDateTime.now();
        this.actualizadoEn = LocalDateTime.now();

        if (this.stock == null) {
            this.stock = 0;
        }

        if (this.estado == null) {
            this.estado = this.stock > 0 ? EstadoLibro.DISPONIBLE : EstadoLibro.AGOTADO;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.actualizadoEn = LocalDateTime.now();

        // Si el stock llega a 0, el libro pasa automáticamente a agotado.
        if (this.stock != null && this.stock <= 0 && this.estado != EstadoLibro.DESCONTINUADO) {
            this.estado = EstadoLibro.AGOTADO;
        }
    }
}