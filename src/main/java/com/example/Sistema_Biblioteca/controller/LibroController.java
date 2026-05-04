package com.example.Sistema_Biblioteca.controller;

import com.example.Sistema_Biblioteca.entity.Libro;
import com.example.Sistema_Biblioteca.enums.EstadoLibro;
import com.example.Sistema_Biblioteca.exception.BusinessException;
import com.example.Sistema_Biblioteca.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroRepository libroRepository;

    @GetMapping
    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    @PostMapping
    public Libro guardarLibro(@RequestBody Libro libro) {
        if (libro.getTitulo() == null || libro.getTitulo().isBlank()) {
            throw new BusinessException("El título es obligatorio");
        }

        if (libro.getAutor() == null || libro.getAutor().isBlank()) {
            throw new BusinessException("El autor es obligatorio");
        }

        if (libro.getIsbn() == null || libro.getIsbn().isBlank()) {
            throw new BusinessException("El ISBN es obligatorio");
        }

        if (libroRepository.existsByIsbn(libro.getIsbn())) {
            throw new BusinessException("Ya existe un libro con ese ISBN");
        }

        if (libro.getPrecioVenta() == null || libro.getPrecioVenta().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("El precio de venta debe ser mayor a 0");
        }

        if (libro.getStock() == null) {
            libro.setStock(0);
        }

        if (libro.getActivo() == null) {
            libro.setActivo(true);
        }

        if (libro.getEstado() == null) {
            libro.setEstado(libro.getStock() > 0 ? EstadoLibro.DISPONIBLE : EstadoLibro.AGOTADO);
        }

        return libroRepository.save(libro);
    }
}