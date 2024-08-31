package com.todocode.tpFinal.repository;

import com.todocode.tpFinal.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface IVentaRepository extends JpaRepository<Venta, Long> {
    Optional<Venta> findByCodigoVenta(Long codigoVenta);

    Double findTotalMontoByFechaVenta(LocalDate fechaVenta);

    Long countVentasByFechaVenta(LocalDate fechaVenta);

    Optional<Venta> findVentaConMayorMonto();
}
