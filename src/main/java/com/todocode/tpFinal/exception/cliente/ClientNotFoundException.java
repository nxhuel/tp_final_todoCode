package com.todocode.tpFinal.exception.cliente;

public class ClientNotFoundException extends RuntimeException{
    public ClientNotFoundException(Long idCliente) {
        super(String.format("No se encontro cliente con id: %d", idCliente));
    }
}
