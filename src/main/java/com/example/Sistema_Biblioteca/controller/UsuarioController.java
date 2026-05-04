package com.example.Sistema_Biblioteca.controller;

import com.example.Sistema_Biblioteca.entity.Usuario;
import com.example.Sistema_Biblioteca.enums.Rol;
import com.example.Sistema_Biblioteca.exception.BusinessException;
import com.example.Sistema_Biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @PostMapping
    public Usuario guardarUsuario(@RequestBody Usuario usuario) {
        if (usuario.getNombre() == null || usuario.getNombre().isBlank()) {
            throw new BusinessException("El nombre es obligatorio");
        }

        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            throw new BusinessException("El email es obligatorio");
        }

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new BusinessException("Ya existe un usuario con ese email");
        }

        if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
            throw new BusinessException("La contraseña es obligatoria");
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        if (usuario.getRol() == null) {
            usuario.setRol(Rol.ROLE_CLIENT);
        }

        if (usuario.getActivo() == null) {
            usuario.setActivo(true);
        }

        return usuarioRepository.save(usuario);
    }
}