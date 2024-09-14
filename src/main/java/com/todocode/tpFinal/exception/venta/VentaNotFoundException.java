package com.todocode.tpFinal.exception.venta;

public class VentaNotFoundException extends RuntimeException{
    public VentaNotFoundException(Long codigoVenta) {
        super(String.format("No se encontro el codigo con el id: %d", codigoVenta));
    }
}
