package com.todocode.tpFinal.service;

import com.todocode.tpFinal.model.Producto;

import java.util.List;

public interface IProductoService {
    public List<Producto> getProductos();

    public Producto findByProducto(Long codigoProducto);

    public Producto createProducto(Producto producto);

    public void deleteProducto(Long codigoProducto);

    public Producto updateProducto(Long codigoProducto, String nuevoNombre, String nuevaMarca, Double nuevoCosto, Double nuevaCantidadDisponible);

    public List<Producto> getProductosConBajoStock();
}
