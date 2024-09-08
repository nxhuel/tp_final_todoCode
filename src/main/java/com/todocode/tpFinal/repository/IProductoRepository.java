package com.todocode.tpFinal.repository;

import com.todocode.tpFinal.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Long> {

    @Query("SELECT p FROM Producto p WHERE p.cantidadDisponible < :cantidadDisponible")
    List<Producto> findByCantidadDisponibleLessThan(Double cantidadDisponible);
}