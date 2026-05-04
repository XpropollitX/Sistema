package com.example.Sistema_Biblioteca.repository;

import com.example.Sistema_Biblioteca.entity.Usuario;
import com.example.Sistema_Biblioteca.enums.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Usuario> findByActivoTrue();

    List<Usuario> findByRol(Rol rol);

    long countByActivoTrue();
}