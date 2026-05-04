package com.example.Sistema_Biblioteca.service;

import com.example.Sistema_Biblioteca.entity.*;
import com.example.Sistema_Biblioteca.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlquilerService {

    @Autowired
    private AlquilerRepository alquilerRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;


        // GUARDAR ALQUILER

    public Alquiler guardarAlquiler(Alquiler alquiler) {

        // 1. Validar usuario
        Usuario usuario = usuarioRepository.findById(alquiler.getUsuario().getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        alquiler.setUsuario(usuario);

        // 2. Estado inicial
        alquiler.setEstado("ACTIVO");

        // 3. Recorrer detalles
        for (DetalleAlquiler det : alquiler.getDetalles()) {

            // 4. Obtener libro
            Libro libro = libroRepository.findById(det.getLibro().getIdLibro())
                    .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

            // 5. Validar stock
            if (libro.getStock() < det.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + libro.getTitulo());
            }

            // 6. Asignar precio alquiler
            det.setPrecioUnitario(libro.getPrecioAlquiler());

            // 7. Calcular subtotal
            double subtotal = det.getCantidad() * det.getPrecioUnitario();
            det.setSubtotal(subtotal);

            // 8. Descontar stock
            libro.setStock(libro.getStock() - det.getCantidad());
            libroRepository.save(libro);

            // 9. Relación detalle → alquiler
            det.setAlquiler(alquiler);
        }

        // 10. Guardar todo
        return alquilerRepository.save(alquiler);
    }

       // DEVOLVER ALQUILER

    public Alquiler devolverAlquiler(Long idAlquiler) {

        // 1. Buscar alquiler
        Alquiler alquiler = alquilerRepository.findById(idAlquiler)
                .orElseThrow(() -> new RuntimeException("Alquiler no encontrado"));

        // 2. Devolver stock
        for (DetalleAlquiler det : alquiler.getDetalles()) {
            Libro libro = det.getLibro();
            libro.setStock(libro.getStock() + det.getCantidad());
            libroRepository.save(libro);
        }

        // 3. Cambiar estado
        alquiler.setEstado("DEVUELTO");

        // 4. Guardar cambios
        return alquilerRepository.save(alquiler);
    }
}