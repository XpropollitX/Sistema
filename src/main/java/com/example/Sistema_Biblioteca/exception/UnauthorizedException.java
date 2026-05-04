package com.example.Sistema_Biblioteca.exception;

/**
 * Excepción para solicitudes no autorizadas.
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }
}