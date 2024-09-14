package com.todocode.tpFinal.exception.producto;

public class ProductNoDataFoundException extends RuntimeException{
    public ProductNoDataFoundException() {
        super(String.format("No se encontraron productos"));
    }
}
