package com.todocode.tpFinal.service;

import com.todocode.tpFinal.model.Producto;
import com.todocode.tpFinal.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    private IProductoRepository iProductoRepository;

    @Override
    public List<Producto> getProductos() {
        return iProductoRepository.findAll();
    }

    @Override
    public Producto findByProducto(Long codigoProducto) {
        return iProductoRepository.findById(codigoProducto).orElse(null);
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

        nuevoProducto.setNombre(nuevoNombre);
        nuevoProducto.setMarca(nuevaMarca);
        nuevoProducto.setCosto(nuevoCosto);
        nuevoProducto.setCantidadDisponible(nuevaCantidadDisponible);

        this.createProducto(nuevoProducto);
    }

    @Override
    public List<Producto> getProductosConBajoStock() {
        return iProductoRepository.findByCantidadDisponibleLessThan(5.0);
    }
}
