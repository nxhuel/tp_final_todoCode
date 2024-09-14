package com.todocode.tpFinal.exception.producto;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(Long codigoProducto) {
        super(String.format("No se encontro el producto con el id: %d", codigoProducto));
    }
}
