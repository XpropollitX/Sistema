package com.example.Sistema_Biblioteca.repository;

import com.example.Sistema_Biblioteca.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    Libro findByIsbn(String isbn);
}
