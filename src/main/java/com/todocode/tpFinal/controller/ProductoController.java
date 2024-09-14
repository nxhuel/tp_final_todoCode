package com.todocode.tpFinal.controller;

import com.todocode.tpFinal.model.Producto;
import com.todocode.tpFinal.service.IProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductoController {

    @Autowired
    private IProductoService iProductoService;

    @GetMapping("/productos")
    public List<Producto> getProductos() {
        return iProductoService.getProductos();
    }

    @GetMapping("/productos/{codigoProducto}")
    public Producto findByProducto(@PathVariable Long codigoProducto) {
        return iProductoService.findByProducto(codigoProducto);
    }

    @PostMapping("/productos/crear")
    public ResponseEntity<String> createProducto(@RequestBody @Valid Producto producto) {
        iProductoService.createProducto(producto);
        return new ResponseEntity<>("Producto agregado con éxito", HttpStatus.CREATED);
    }

    @DeleteMapping("/productos/eliminar/{codigoProducto}")
    public ResponseEntity<String> deleteProducto(@PathVariable Long codigoProducto) {
        iProductoService.deleteProducto(codigoProducto);
        return new ResponseEntity<>("Producto eliminado con éxito", HttpStatus.OK);
    }

    @PutMapping("/productos/editar/{codigoProducto}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long codigoProducto,
                                                   @RequestParam(required = false, value = "nombre") String nuevoNombre,
                                                   @RequestParam(required = false, value = "marca") String nuevaMarca,
                                                   @RequestParam(required = false, value = "costo") Double nuevoCosto,
                                                   @RequestParam(required = false, value = "cantidadDisponible") Double nuevaCantidadDisponible) {
        iProductoService.updateProducto(codigoProducto, nuevoNombre, nuevaMarca, nuevoCosto, nuevaCantidadDisponible);
        Producto producto = iProductoService.findByProducto(codigoProducto);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @GetMapping("/productos/faltaStock")
    public List<Producto> getProductosConBajoStock() {
        return iProductoService.getProductosConBajoStock();
    }
}
