package com.example.Sistema_Biblioteca.service;

import com.example.Sistema_Biblioteca.dto.request.CambioEstadoRequest;
import com.example.Sistema_Biblioteca.dto.request.LibroRequest;
import com.example.Sistema_Biblioteca.entity.Libro;
import com.example.Sistema_Biblioteca.enums.EstadoLibro;
import com.example.Sistema_Biblioteca.exception.BusinessException;
import com.example.Sistema_Biblioteca.exception.ResourceNotFoundException;
import com.example.Sistema_Biblioteca.repository.LibroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibroService {

    private final LibroRepository libroRepository;

    public List<Libro> listarTodos() {
        return libroRepository.findAll();
    }

    public List<Libro> listarActivos() {
        return libroRepository.findByActivoTrue();
    }

    public List<Libro> listarDisponibles() {
        return libroRepository.findByActivoTrueAndEstado(EstadoLibro.DISPONIBLE);
    }

    public Libro buscarPorId(Long idLibro) {
        return libroRepository.findById(idLibro)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado"));
    }

    public List<Libro> buscarPorTituloOAutor(String q) {
        return libroRepository
                .findByActivoTrueAndTituloContainingIgnoreCaseOrActivoTrueAndAutorContainingIgnoreCase(q, q);
    }

    public List<Libro> buscarPorCategoria(String categoria) {
        return libroRepository.findByActivoTrueAndCategoriaIgnoreCase(categoria);
    }

    public Libro guardarLibro(Libro libro) {
        if (libroRepository.existsByIsbn(libro.getIsbn())) {
            throw new BusinessException("Ya existe un libro con ese ISBN");
        }

        if (libro.getActivo() == null) {
            libro.setActivo(true);
        }

        if (libro.getEstado() == null) {
            libro.setEstado(libro.getStock() > 0 ? EstadoLibro.DISPONIBLE : EstadoLibro.AGOTADO);
        }

        return libroRepository.save(libro);
    }

    public Libro crearDesdeRequest(LibroRequest request) {
        if (libroRepository.existsByIsbn(request.getIsbn())) {
            throw new BusinessException("Ya existe un libro con ese ISBN");
        }

        Libro libro = Libro.builder()
                .titulo(request.getTitulo())
                .autor(request.getAutor())
                .isbn(request.getIsbn())
                .precioVenta(request.getPrecioVenta())
                .precioAlquiler(request.getPrecioAlquiler())
                .stock(request.getStock())
                .categoria(request.getCategoria())
                .descripcion(request.getDescripcion())
                .urlPortada(request.getUrlPortada())
                .estado(request.getEstado() != null ? request.getEstado() : EstadoLibro.DISPONIBLE)
                .activo(true)
                .build();

        return libroRepository.save(libro);
    }

    public Libro editarLibro(Long idLibro, LibroRequest request) {
        Libro libro = buscarPorId(idLibro);

        libro.setTitulo(request.getTitulo());
        libro.setAutor(request.getAutor());
        libro.setIsbn(request.getIsbn());
        libro.setPrecioVenta(request.getPrecioVenta());
        libro.setPrecioAlquiler(request.getPrecioAlquiler());
        libro.setStock(request.getStock());
        libro.setCategoria(request.getCategoria());
        libro.setDescripcion(request.getDescripcion());
        libro.setUrlPortada(request.getUrlPortada());

        if (request.getEstado() != null) {
            libro.setEstado(request.getEstado());
        }

        return libroRepository.save(libro);
    }

    public Libro cambiarEstado(Long idLibro, CambioEstadoRequest request) {
        Libro libro = buscarPorId(idLibro);
        libro.setEstado(request.getEstado());
        return libroRepository.save(libro);
    }

    public void eliminarSoft(Long idLibro) {
        Libro libro = buscarPorId(idLibro);
        libro.setActivo(false);
        libro.setEstado(EstadoLibro.DESCONTINUADO);
        libroRepository.save(libro);
    }
}