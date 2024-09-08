package com.todocode.tpFinal.controller;

import com.todocode.tpFinal.dto.DetalleVentaDTO;
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

    @PostMapping(value = "/ventas/crear")
    public String createVenta(@RequestBody Venta venta) {
        iVentaService.createVenta(venta);
        return "Venta agregada con éxito";
    }

    @DeleteMapping("/ventas/eliminar/{codigoVenta}")
    public String deleteVenta(@PathVariable Long codigoVenta) {
        iVentaService.deleteVenta(codigoVenta);
        return "Venta eliminada con éxito";
    }

    @PutMapping("/ventas/editar/{codigoVenta}")
    public Venta updateVenta(@PathVariable Long codigoVenta,
                             @RequestParam (required = false, value = "fechaVenta") LocalDate nuevaFechaVenta,
                             @RequestParam (required = false, value = "total") Double nuevoTotal,
                             @RequestParam (required = false, value = "listaProductos") List<Producto> nuevaListaProductos,
                             @RequestParam (required = false, value = "unCliente") Cliente nuevoCliente) {
        iVentaService.updateVenta(codigoVenta, nuevaFechaVenta, nuevoTotal, nuevaListaProductos, nuevoCliente);
        Venta venta = iVentaService.findByVenta(codigoVenta);
        return venta;
    }

    @GetMapping("/ventas/productos/{codigoVenta}")
    public List<Producto> getProductosByVenta(@PathVariable Long codigoVenta) {
        return iVentaService.getProductosByVenta(codigoVenta);
    }

    @GetMapping("/ventas/resultados/{fechaVenta}")
    public String getResultadoVentas(@PathVariable LocalDate fechaVenta) {
        return iVentaService.getResultadoVentas(fechaVenta);
    }

    @GetMapping("/ventas/mayorVenta")
    public DetalleVentaDTO getVentaMayorMonto() {
        return iVentaService.getVentaConMayorMonto();
    }
}
