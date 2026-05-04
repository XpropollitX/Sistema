package com.example.Sistema_Biblioteca.exception;

/**
 * Excepción para errores de negocio del sistema.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}