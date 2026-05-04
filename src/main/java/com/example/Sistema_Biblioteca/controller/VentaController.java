package com.example.Sistema_Biblioteca.controller;

import com.example.Sistema_Biblioteca.entity.Venta;
import com.example.Sistema_Biblioteca.exception.BusinessException;
import com.example.Sistema_Biblioteca.repository.VentaRepository;
import com.example.Sistema_Biblioteca.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private VentaRepository ventaRepository;

    @GetMapping
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    @PostMapping
    public Venta guardarVenta(@RequestBody Venta venta) {
        if (venta.getUsuario() == null || venta.getUsuario().getIdUsuario() == null) {
            throw new BusinessException("Debe enviar el id del usuario");
        }

        if (venta.getDetalles() == null || venta.getDetalles().isEmpty()) {
            throw new BusinessException("La venta debe tener al menos un detalle");
        }

        return ventaService.guardarVenta(venta);
    }
}