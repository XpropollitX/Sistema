package com.example.Sistema_Biblioteca.controller;

import com.example.Sistema_Biblioteca.entity.Venta;
import com.example.Sistema_Biblioteca.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ventas")
@CrossOrigin(origins = "*")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    // GUARDAR VENTA
    @PostMapping
    public ResponseEntity<?> guardarVenta(@RequestBody Venta venta) {
        System.out.println("VENTA RECIBIDA: " + venta);
        return ResponseEntity.ok("OK MAPEADO");
    }
}