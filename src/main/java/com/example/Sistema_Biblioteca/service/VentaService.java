package com.example.Sistema_Biblioteca.service;

import com.example.Sistema_Biblioteca.dto.response.ClienteResumenResponse;
import com.example.Sistema_Biblioteca.dto.response.DetalleVentaResponse;
import com.example.Sistema_Biblioteca.dto.response.VentaResponse;
import com.example.Sistema_Biblioteca.entity.DetalleVenta;
import com.example.Sistema_Biblioteca.entity.Libro;
import com.example.Sistema_Biblioteca.entity.Usuario;
import com.example.Sistema_Biblioteca.entity.Venta;
import com.example.Sistema_Biblioteca.enums.EstadoLibro;
import com.example.Sistema_Biblioteca.enums.EstadoVenta;
import com.example.Sistema_Biblioteca.exception.BusinessException;
import com.example.Sistema_Biblioteca.exception.ResourceNotFoundException;
import com.example.Sistema_Biblioteca.exception.StockInsuficienteException;
import com.example.Sistema_Biblioteca.repository.LibroRepository;
import com.example.Sistema_Biblioteca.repository.UsuarioRepository;
import com.example.Sistema_Biblioteca.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Transactional(readOnly = true)
    public List<VentaResponse> listarVentas() {

        return ventaRepository.findAll().stream().map(v -> {

            ClienteResumenResponse cliente = new ClienteResumenResponse(
                    v.getUsuario().getIdUsuario(),
                    v.getUsuario().getNombre(),
                    v.getUsuario().getEmail()
            );

            List<DetalleVentaResponse> detalles = v.getDetalles().stream()
                    .map(d -> new DetalleVentaResponse(
                            d.getLibro().getIdLibro(),
                            d.getLibro().getTitulo(),
                            d.getLibro().getAutor(),
                            d.getCantidad(),
                            d.getPrecioUnitario(),
                            d.getSubtotal()
                    ))
                    .toList();

            return new VentaResponse(
                    v.getIdVenta(),
                    v.getEstado(),
                    v.getTotal(),
                    v.getCreadoEn(),
                    cliente,
                    detalles,
                    "OK"
            );
        }).toList();
    }

    @Transactional
    public Venta guardarVenta(Venta venta) {

        Usuario usuario = usuarioRepository.findById(venta.getUsuario().getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if (venta.getDetalles() == null || venta.getDetalles().isEmpty()) {
            throw new BusinessException("La venta debe tener al menos un detalle");
        }

        venta.setUsuario(usuario);
        venta.setEstado(EstadoVenta.COMPLETADA);

        BigDecimal total = BigDecimal.ZERO;

        for (DetalleVenta detalle : venta.getDetalles()) {

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

            BigDecimal precioUnitario = libro.getPrecioVenta();

            BigDecimal subtotal = precioUnitario.multiply(
                    BigDecimal.valueOf(detalle.getCantidad())
            );

            detalle.setVenta(venta);
            detalle.setLibro(libro);
            detalle.setPrecioUnitario(precioUnitario);
            detalle.setSubtotal(subtotal);

            libro.setStock(libro.getStock() - detalle.getCantidad());

            if (libro.getStock() == 0) {
                libro.setEstado(EstadoLibro.AGOTADO);
            }

            total = total.add(subtotal);
        }

        venta.setTotal(total);

        return ventaRepository.save(venta);
    }
}