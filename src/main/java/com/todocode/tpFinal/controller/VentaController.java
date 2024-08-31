package com.todocode.tpFinal.controller;

import com.todocode.tpFinal.dto.VentaDTO;
import com.todocode.tpFinal.model.Cliente;
import com.todocode.tpFinal.model.Producto;
import com.todocode.tpFinal.model.Venta;
import com.todocode.tpFinal.service.IVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class VentaController {

    @Autowired
    private IVentaService iVentaService;

    @GetMapping("/ventas")
    public List<Venta> getVentas() {
        return iVentaService.getVentas();
    }

    @GetMapping("/ventas/{codigoVenta}")
    public Venta findByVenta(@PathVariable Long codigoVenta) {
        return iVentaService.findByVenta(codigoVenta);
    }

    @PostMapping("/ventas/crear")
    public String createVenta(@RequestBody Venta venta) {
        iVentaService.createVenta(venta);
        return "Venta agregada con éxito";
    }

    @DeleteMapping("/ventas/eliminar/{codigoVenta}")
    public String deleteVenta(Long codigoVenta) {
        iVentaService.deleteVenta(codigoVenta);
        return "Venta eliminada con éxito";
    }

    @PutMapping("/ventas/editar/{codigoVenta}")
    public Venta updateVenta(Long codigoVenta, LocalDate nuevaFechaVenta, Double nuevoTotal, List<Producto> nuevaListaProductos, Cliente nuevoCliente) {
        iVentaService.updateVenta(codigoVenta, nuevaFechaVenta, nuevoTotal, nuevaListaProductos, nuevoCliente);
        Venta venta = iVentaService.findByVenta(codigoVenta);
        return venta;
    }

    @GetMapping("/ventas/productos/{codigoVenta}")
    public List<Producto> getProductosByVenta(@PathVariable Long codigoVenta) {
        return iVentaService.getProductosByVenta(codigoVenta);
    }

    @GetMapping("/ventas/{fechaVenta")
    public String getResultadoVentas(@PathVariable LocalDate fechaVenta) {
        return iVentaService.getResultadoVentas(fechaVenta);
    }

    @GetMapping("/ventas/mayorVenta")
    public ResponseEntity<VentaDTO> getVentaMayorMonto() {
        VentaDTO ventaDTO = iVentaService.getVentaConMayorMonto();
        if (ventaDTO != null) {
            return ResponseEntity.ok(ventaDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
