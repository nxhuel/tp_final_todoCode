package com.todocode.tpFinal.service;

import com.todocode.tpFinal.dto.VentaDTO;
import com.todocode.tpFinal.model.Cliente;
import com.todocode.tpFinal.model.Producto;
import com.todocode.tpFinal.model.Venta;
import com.todocode.tpFinal.repository.IVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<Producto> getProductosByVenta(Long codigoVenta) {
        Venta venta = iVentaRepository.findByCodigoVenta(codigoVenta).orElse(null);
        return venta.getListaProductos();
    }

    @Override
    public String getResultadoVentas(LocalDate fechaVenta) {
        Double montoTotal = iVentaRepository.findTotalMontoByFechaVenta(fechaVenta);
        Long cantidadVentas = iVentaRepository.countVentasByFechaVenta(fechaVenta);

        return String.format("Monto: %.2f\nVentas Totales: %d", montoTotal != null ? montoTotal : 0.0, cantidadVentas);
    }

    @Override
    public VentaDTO getVentaConMayorMonto() {
        Optional<Venta> ventaOptional = iVentaRepository.findVentaConMayorMonto();
        if (ventaOptional.isPresent()) {
            Venta venta = ventaOptional.get();
            Double cantidadDisponible = venta.getListaProductos() != null ? venta.getListaProductos().size() : 0.0;
            Cliente cliente = venta.getUnCliente();
            String nombre = cliente != null ? cliente.getNombre() : "";
            String apellido = cliente != null ? cliente.getApellido() : "";

            return new VentaDTO(
                    venta.getCodigoVenta(),
                    venta.getTotal(),
                    cantidadDisponible,
                    nombre,
                    apellido
            );
        }
        return null;
    }
}
