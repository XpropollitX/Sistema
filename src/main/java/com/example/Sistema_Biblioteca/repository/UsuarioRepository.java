package com.example.Sistema_Biblioteca.repository;

import com.example.Sistema_Biblioteca.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // metodos
    Usuario findByEmail(String email);
}