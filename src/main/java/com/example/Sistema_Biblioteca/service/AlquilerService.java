package com.example.Sistema_Biblioteca.service;

import com.example.Sistema_Biblioteca.entity.Alquiler;
import com.example.Sistema_Biblioteca.entity.DetalleAlquiler;
import com.example.Sistema_Biblioteca.entity.Libro;
import com.example.Sistema_Biblioteca.entity.Usuario;
import com.example.Sistema_Biblioteca.enums.EstadoAlquiler;
import com.example.Sistema_Biblioteca.enums.EstadoLibro;
import com.example.Sistema_Biblioteca.exception.BusinessException;
import com.example.Sistema_Biblioteca.exception.ResourceNotFoundException;
import com.example.Sistema_Biblioteca.exception.StockInsuficienteException;
import com.example.Sistema_Biblioteca.repository.AlquilerRepository;
import com.example.Sistema_Biblioteca.repository.LibroRepository;
import com.example.Sistema_Biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Servicio encargado de gestionar alquileres de libros.
 * Aquí se valida stock, se calcula subtotal, total y se controla la devolución.
 */
@Service
public class AlquilerService {

    @Autowired
    private AlquilerRepository alquilerRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Transactional
    public Alquiler guardarAlquiler(Alquiler alquiler) {

        Usuario usuario = usuarioRepository.findById(alquiler.getUsuario().getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if (alquiler.getDetalles() == null || alquiler.getDetalles().isEmpty()) {
            throw new BusinessException("El alquiler debe tener al menos un detalle");
        }

        alquiler.setUsuario(usuario);
        alquiler.setEstado(EstadoAlquiler.ACTIVO);

        BigDecimal total = BigDecimal.ZERO;

        for (DetalleAlquiler detalle : alquiler.getDetalles()) {

            if (detalle.getCantidad() == null || detalle.getCantidad() <= 0) {
                throw new BusinessException("La cantidad debe ser mayor a 0");
            }

            Libro libro = libroRepository.findById(detalle.getLibro().getIdLibro())
                    .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado"));

            if (!libro.getEstado().equals(EstadoLibro.DISPONIBLE)) {
                throw new BusinessException("Libro no disponible: " + libro.getTitulo());
            }

            if (libro.getStock() < detalle.getCantidad()) {
                throw new StockInsuficienteException("Sin stock disponible para: " + libro.getTitulo());
            }

            if (libro.getPrecioAlquiler() == null) {
                throw new BusinessException("El libro no tiene precio de alquiler configurado: " + libro.getTitulo());
            }

            BigDecimal precioUnitario = libro.getPrecioAlquiler();

            BigDecimal subtotal = precioUnitario.multiply(
                    BigDecimal.valueOf(detalle.getCantidad())
            );

            detalle.setAlquiler(alquiler);
            detalle.setLibro(libro);
            detalle.setPrecioUnitario(precioUnitario);
            detalle.setSubtotal(subtotal);

            libro.setStock(libro.getStock() - detalle.getCantidad());

            if (libro.getStock() == 0) {
                libro.setEstado(EstadoLibro.AGOTADO);
            }

            total = total.add(subtotal);
        }

        alquiler.setTotal(total);

        return alquilerRepository.save(alquiler);
    }

    @Transactional
    public Alquiler devolverAlquiler(Long idAlquiler) {

        Alquiler alquiler = alquilerRepository.findById(idAlquiler)
                .orElseThrow(() -> new ResourceNotFoundException("Alquiler no encontrado"));

        if (alquiler.getEstado().equals(EstadoAlquiler.DEVUELTO)) {
            throw new BusinessException("El alquiler ya fue devuelto");
        }

        alquiler.setFechaDevolucion(LocalDate.now());
        alquiler.setEstado(EstadoAlquiler.DEVUELTO);

        for (DetalleAlquiler detalle : alquiler.getDetalles()) {
            Libro libro = detalle.getLibro();

            libro.setStock(libro.getStock() + detalle.getCantidad());

            if (libro.getStock() > 0 && libro.getEstado().equals(EstadoLibro.AGOTADO)) {
                libro.setEstado(EstadoLibro.DISPONIBLE);
            }
        }

        return alquilerRepository.save(alquiler);
    }
}