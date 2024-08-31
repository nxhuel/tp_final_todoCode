package com.todocode.tpFinal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VentaDTO {
    private Long codigoVenta;
    private Double total;
    private Double cantidadDisponible;
    private String nombre;
    private String apellido;

    public VentaDTO() {
    }

    public VentaDTO(Long codigoVenta, Double total, Double cantidadDisponible, String nombre, String apellido) {
        this.codigoVenta = codigoVenta;
        this.total = total;
        this.cantidadDisponible = cantidadDisponible;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        return "VentaDTO{" +
                "codigoVenta=" + codigoVenta +
                ", total=" + total +
                ", cantidadDisponible=" + cantidadDisponible +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                '}';
    }
}
