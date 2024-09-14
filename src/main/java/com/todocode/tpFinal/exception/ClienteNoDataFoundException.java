package com.todocode.tpFinal.exception;

public class ClienteNoDataFoundException extends RuntimeException{
    public ClienteNoDataFoundException() {
        super(String.format("No se encontraron datos de clientes"));
    }
}
