package com.example.Sistema_Biblioteca.repository;

import com.example.Sistema_Biblioteca.entity.Libro;
import com.example.Sistema_Biblioteca.enums.EstadoLibro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);

    List<Libro> findByActivoTrue();

    List<Libro> findByActivoTrueAndEstado(EstadoLibro estado);

    List<Libro> findByActivoTrueAndCategoriaIgnoreCase(String categoria);

    List<Libro> findByActivoTrueAndTituloContainingIgnoreCaseOrActivoTrueAndAutorContainingIgnoreCase(
            String titulo,
            String autor
    );

    long countByActivoTrue();
}