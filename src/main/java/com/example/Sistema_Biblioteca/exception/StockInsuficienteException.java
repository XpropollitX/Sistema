package com.example.Sistema_Biblioteca.exception;

/**
 * Excepción específica para errores de stock insuficiente.
 */
public class StockInsuficienteException extends BusinessException {

    public StockInsuficienteException(String message) {
        super(message);
    }
}