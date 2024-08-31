package com.todocode.tpFinal.service;

import com.todocode.tpFinal.model.Cliente;
import com.todocode.tpFinal.model.Producto;
import com.todocode.tpFinal.model.Venta;
import com.todocode.tpFinal.repository.IVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VentaService implements IVentaService{

    @Autowired
    private IVentaRepository iVentaRepository;

    @Override
    public List<Venta> getVentas() {
        return iVentaRepository.findAll();
    }

    @Override
    public Venta findByVenta(Long codigoVenta) {
        return iVentaRepository.findById(codigoVenta).orElse(null);
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

        nuevaVenta.setFechaVenta(nuevaFechaVenta);
        nuevaVenta.setTotal(nuevoTotal);
        nuevaVenta.setListaProductos(nuevaListaProductos);
        nuevaVenta.setUnCliente(nuevoCliente);

        this.createVenta(nuevaVenta);
    }
}
