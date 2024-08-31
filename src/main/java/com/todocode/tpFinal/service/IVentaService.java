package com.todocode.tpFinal.service;

import com.todocode.tpFinal.model.Cliente;
import com.todocode.tpFinal.model.Producto;
import com.todocode.tpFinal.model.Venta;

import java.time.LocalDate;
import java.util.List;

public interface IVentaService {
    public List<Venta> getVentas();

    public Venta findByVenta(Long codigoVenta);

    public void createVenta(Venta venta);

    public void deleteVenta(Long codigoVenta);

    public void updateVenta(Long codigoVenta, LocalDate nuevaFechaVenta, Double nuevoTotal, List<Producto> nuevaListaProductos, Cliente nuevoCliente);
}
