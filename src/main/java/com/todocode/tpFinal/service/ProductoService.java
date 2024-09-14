package com.todocode.tpFinal.service;

import com.todocode.tpFinal.exception.producto.ProductNoDataFoundException;
import com.todocode.tpFinal.exception.producto.ProductNotFoundException;
import com.todocode.tpFinal.model.Producto;
import com.todocode.tpFinal.model.Venta;
import com.todocode.tpFinal.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    private IProductoRepository iProductoRepository;

    @Override
    public List<Producto> getProductos() {
        var productos = (List<Producto>) iProductoRepository.findAll();
        if (productos.isEmpty()) {
            throw new ProductNoDataFoundException();
        }
        return productos;
    }

    @Override
    public Producto findByProducto(Long codigoProducto) {
        return iProductoRepository.findById(codigoProducto)
                .orElseThrow(() -> new ProductNotFoundException(codigoProducto));
    }

    @Override
    public void createProducto(Producto producto) {
        iProductoRepository.save(producto);
    }

    @Override
    public void deleteProducto(Long codigoProducto) {
        iProductoRepository.deleteById(codigoProducto);
    }

    @Override
    public void updateProducto(Long codigoProducto, String nuevoNombre, String nuevaMarca, Double nuevoCosto, Double nuevaCantidadDisponible) {
        Producto nuevoProducto = this.findByProducto(codigoProducto);
        if (nuevoProducto == null) {
            throw new ProductNotFoundException(codigoProducto);
        }

        nuevoProducto.setNombre(nuevoNombre);
        nuevoProducto.setMarca(nuevaMarca);
        nuevoProducto.setCosto(nuevoCosto);
        nuevoProducto.setCantidadDisponible(nuevaCantidadDisponible);

        this.createProducto(nuevoProducto);
    }

    @Override
    public List<Producto> getProductosConBajoStock() {
        List<Producto> faltantes = new ArrayList<>();
        List<Producto> todos = this.getProductos();

        if (todos.isEmpty()) {
            throw new ProductNoDataFoundException();
        }

        for (Producto producto : todos) {
            if (producto.getCantidadDisponible() <= 5) {
                faltantes.add(producto);
            }
        }
        return faltantes;
    }
}
