package com.todocode.tpFinal.exception.venta;

public class VentaNoDataFoundException extends RuntimeException{
    public VentaNoDataFoundException() {
        super(String.format("No se encontraron datos de la venta"));
    }
}
