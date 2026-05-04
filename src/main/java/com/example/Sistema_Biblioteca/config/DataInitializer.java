package com.example.Sistema_Biblioteca.config;

import com.example.Sistema_Biblioteca.entity.Libro;
import com.example.Sistema_Biblioteca.entity.Usuario;
import com.example.Sistema_Biblioteca.enums.EstadoLibro;
import com.example.Sistema_Biblioteca.enums.Rol;
import com.example.Sistema_Biblioteca.repository.LibroRepository;
import com.example.Sistema_Biblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        crearUsuariosBase();
        crearLibrosBase();
    }

    private void crearUsuariosBase() {
        crearUsuarioSiNoExiste("Administrador", "admin@biblioteca.com", "admin123", Rol.ROLE_ADMIN);
        crearUsuarioSiNoExiste("Empleado Biblioteca", "empleado@biblioteca.com", "empleado123", Rol.ROLE_EMPLEADO);
        crearUsuarioSiNoExiste("Carlos Cliente", "cliente@biblioteca.com", "cliente123", Rol.ROLE_CLIENT);
    }

    private void crearUsuarioSiNoExiste(String nombre, String email, String password, Rol rol) {
        if (usuarioRepository.existsByEmail(email)) {
            return;
        }

        Usuario usuario = Usuario.builder()
                .nombre(nombre)
                .email(email)
                .password(passwordEncoder.encode(password))
                .rol(rol)
                .activo(true)
                .build();

        usuarioRepository.save(usuario);
    }

    private void crearLibrosBase() {
        if (libroRepository.count() > 0) {
            return;
        }

        crearLibro("Cien años de soledad", "Gabriel García Márquez", "9780307474728", "Novela",
                "Clásico de la literatura latinoamericana.",
                "https://covers.openlibrary.org/b/isbn/9780307474728-L.jpg",
                "45.00", "10.00", 15);

        crearLibro("El Principito", "Antoine de Saint-Exupéry", "9780156012195", "Fábula",
                "Obra corta sobre amistad, infancia y sentido de vida.",
                "https://covers.openlibrary.org/b/isbn/9780156012195-L.jpg",
                "22.00", "6.00", 30);

        crearLibro("1984", "George Orwell", "9780451524935", "Distopía",
                "Novela distópica sobre vigilancia y control social.",
                "https://covers.openlibrary.org/b/isbn/9780451524935-L.jpg",
                "38.00", "8.00", 12);

        crearLibro("Don Quijote", "Miguel de Cervantes", "9780060934347", "Clásico",
                "Una de las obras más importantes de la literatura universal.",
                "https://covers.openlibrary.org/b/isbn/9780060934347-L.jpg",
                "55.00", "12.00", 8);

        crearLibro("Harry Potter T1", "J. K. Rowling", "9780590353427", "Fantasía",
                "Primera entrega de la saga Harry Potter.",
                "https://covers.openlibrary.org/b/isbn/9780590353427-L.jpg",
                "42.00", "9.00", 20);

        crearLibro("El Alquimista", "Paulo Coelho", "9780061122415", "Novela",
                "Historia de búsqueda personal y destino.",
                "https://covers.openlibrary.org/b/isbn/9780061122415-L.jpg",
                "28.00", "7.00", 25);

        crearLibro("Sapiens", "Yuval Noah Harari", "9780062316097", "Historia",
                "Breve historia de la humanidad.",
                "https://covers.openlibrary.org/b/isbn/9780062316097-L.jpg",
                "48.00", "11.00", 10);

        crearLibro("Clean Code", "Robert C. Martin", "9780132350884", "Programación",
                "Buenas prácticas para escribir código limpio.",
                "https://covers.openlibrary.org/b/isbn/9780132350884-L.jpg",
                "65.00", "15.00", 7);

        crearLibro("Atomic Habits", "James Clear", "9780735211292", "Desarrollo personal",
                "Método práctico para crear buenos hábitos.",
                "https://covers.openlibrary.org/b/isbn/9780735211292-L.jpg",
                "35.00", "8.00", 18);

        crearLibro("La Odisea", "Homero", "9780140268867", "Clásico",
                "Poema épico griego atribuido a Homero.",
                "https://covers.openlibrary.org/b/isbn/9780140268867-L.jpg",
                "30.00", "7.00", 5);

        crearLibro("Libro sin stock", "Autor Demo", "0000000000000", "Prueba",
                "Libro usado para probar validaciones de stock.",
                "https://covers.openlibrary.org/b/isbn/9780132350884-L.jpg",
                "20.00", "5.00", 0);
    }

    private void crearLibro(
            String titulo,
            String autor,
            String isbn,
            String categoria,
            String descripcion,
            String urlPortada,
            String precioVenta,
            String precioAlquiler,
            Integer stock
    ) {
        Libro libro = Libro.builder()
                .titulo(titulo)
                .autor(autor)
                .isbn(isbn)
                .categoria(categoria)
                .descripcion(descripcion)
                .urlPortada(urlPortada)
                .precioVenta(new BigDecimal(precioVenta))
                .precioAlquiler(new BigDecimal(precioAlquiler))
                .stock(stock)
                .estado(stock > 0 ? EstadoLibro.DISPONIBLE : EstadoLibro.AGOTADO)
                .activo(true)
                .build();

        libroRepository.save(libro);
    }
}