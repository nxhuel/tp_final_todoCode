package com.todocode.tpFinal.service;

import com.todocode.tpFinal.dto.DetalleVentaDTO;
import com.todocode.tpFinal.exception.venta.VentaNoDataFoundException;
import com.todocode.tpFinal.exception.venta.VentaNotFoundException;
import com.todocode.tpFinal.model.Cliente;
import com.todocode.tpFinal.model.Producto;
import com.todocode.tpFinal.model.Venta;
import com.todocode.tpFinal.repository.IVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VentaService implements IVentaService {

    @Autowired
    private IVentaRepository iVentaRepository;

    @Override
    public List<Venta> getVentas() {
        var ventas = (List<Venta>) iVentaRepository.findAll();
        if (ventas.isEmpty()) {
            throw new VentaNoDataFoundException();
        }
        return ventas;
    }

    @Override
    public Venta findByVenta(Long codigoVenta) {
        return iVentaRepository.findById(codigoVenta)
                .orElseThrow(()-> new VentaNotFoundException(codigoVenta));
    }

    @Override
    public void createVenta(Venta venta) {
        iVentaRepository.save(venta);
    }

    @Override
    public void deleteVenta(Long codigoVenta) {
        iVentaRepository.deleteById(codigoVenta);
    }

    @Override
    public void updateVenta(Long codigoVenta, LocalDate nuevaFechaVenta, Double nuevoTotal, List<Producto> nuevaListaProductos, Cliente nuevoCliente) {
        Venta nuevaVenta = this.findByVenta(codigoVenta);

        if (nuevaVenta == null) {
            throw new VentaNotFoundException(codigoVenta);
        }

        nuevaVenta.setFechaVenta(nuevaFechaVenta);
        nuevaVenta.setTotal(nuevoTotal);
        nuevaVenta.setListaProductos(nuevaListaProductos);
        nuevaVenta.setCliente(nuevoCliente);

        this.createVenta(nuevaVenta);
    }

    @Override
    public List<Producto> getProductosByVenta(Long codigoVenta) {
        Venta venta = this.findByVenta(codigoVenta);
        return venta.getListaProductos();
    }

    @Override
    public String getResultadoVentas(LocalDate fechaVenta) {
        List<Venta> todos = this.getVentas();

        Double montoTotal = 0.0;
        int cantidadTotal = 0;
        String info = "";

        for (Venta venta : todos) {
            if (venta.getFechaVenta().equals(fechaVenta)) {
                montoTotal += venta.getTotal();
                cantidadTotal++;
            }
        }
        info = String.format("Cantidad de ventas = %.2f\nMonto total = $%d.", montoTotal, cantidadTotal);

        return info;
    }

    @Override
    public DetalleVentaDTO getVentaConMayorMonto() {
        DetalleVentaDTO detalleVentaDTO = new DetalleVentaDTO();

        Venta mayor = new Venta();

        boolean esPrimera = true;
        List<Venta> todos = this.getVentas();

        for (Venta venta : todos) {
            if (esPrimera) {
                mayor = venta;
                esPrimera = false;
            } else if (venta.getTotal() > mayor.getTotal()) {
                mayor = venta;
            }
        }

        detalleVentaDTO.setCodigoVenta(mayor.getCodigoVenta());
        detalleVentaDTO.setTotal(mayor.getTotal());
        detalleVentaDTO.setCantidadProductos((double) mayor.getListaProductos().size());
        detalleVentaDTO.setNombreCliente(mayor.getCliente().getNombre());
        detalleVentaDTO.setApellidoCliente(mayor.getCliente().getApellido());

        return detalleVentaDTO;
    }
}
