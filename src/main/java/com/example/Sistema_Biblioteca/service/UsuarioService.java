package com.example.Sistema_Biblioteca.service;

import com.example.Sistema_Biblioteca.dto.request.CambioEstadoUsuarioRequest;
import com.example.Sistema_Biblioteca.dto.request.CambioRolRequest;
import com.example.Sistema_Biblioteca.entity.Usuario;
import com.example.Sistema_Biblioteca.enums.Rol;
import com.example.Sistema_Biblioteca.exception.BusinessException;
import com.example.Sistema_Biblioteca.exception.ResourceNotFoundException;
import com.example.Sistema_Biblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> listarActivos() {
        return usuarioRepository.findByActivoTrue();
    }

    public Usuario buscarPorId(Long idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    public Usuario guardarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new BusinessException("El email ya está registrado");
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

    public Usuario cambiarRol(Long idUsuario, CambioRolRequest request) {
        Usuario usuario = buscarPorId(idUsuario);
        usuario.setRol(request.getRol());
        return usuarioRepository.save(usuario);
    }

    public Usuario cambiarEstado(Long idUsuario, CambioEstadoUsuarioRequest request) {
        Usuario usuario = buscarPorId(idUsuario);
        usuario.setActivo(request.getActivo());
        return usuarioRepository.save(usuario);
    }
}