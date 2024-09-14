package com.todocode.tpFinal.exception.cliente;

public class ClientNoDataFoundException extends RuntimeException{
    public ClientNoDataFoundException() {
        super(String.format("No se encontraron datos de clientes"));
    }
}
