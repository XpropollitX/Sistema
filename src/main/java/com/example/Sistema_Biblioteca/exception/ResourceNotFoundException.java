package com.example.Sistema_Biblioteca.exception;

/**
 * Excepción para recursos que no existen en la base de datos.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}