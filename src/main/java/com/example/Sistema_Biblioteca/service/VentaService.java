package com.example.Sistema_Biblioteca.service;

import com.example.Sistema_Biblioteca.entity.*;
import com.example.Sistema_Biblioteca.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;

    public Venta guardarVenta(Venta venta) {

        Usuario usuario = usuarioRepository.findById(venta.getUsuario().getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        venta.setUsuario(usuario);

        double total = 0;

        for (DetalleVenta det : venta.getDetalles()) {

            Libro libro = libroRepository.findById(det.getLibro().getIdLibro())
                    .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

            if (libro.getStock() < det.getCantidad()) {
                throw new RuntimeException("Stock insuficiente");
            }

            det.setPrecioUnitario(libro.getPrecioVenta());

            double subtotal = det.getCantidad() * det.getPrecioUnitario();
            det.setSubtotal(subtotal);

            total += subtotal;

            libro.setStock(libro.getStock() - det.getCantidad());
            libroRepository.save(libro);

            det.setVenta(venta);
        }

        venta.setTotal(total);

        return ventaRepository.save(venta);
    }

}