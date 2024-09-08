package com.todocode.tpFinal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleVentaDTO {

    private Long codigoVenta;
    private Double total;
    private Double cantidadProductos;
    private String nombreCliente;
    private String apellidoCliente;

    public DetalleVentaDTO() {

    }
}
