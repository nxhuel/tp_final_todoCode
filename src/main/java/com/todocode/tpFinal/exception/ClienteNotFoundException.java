package com.todocode.tpFinal.exception;

public class ClienteNotFoundException extends RuntimeException{
    public ClienteNotFoundException(Long idCliente) {
        super(String.format("Cliente con id %d no se encontro", idCliente));
    }
}
