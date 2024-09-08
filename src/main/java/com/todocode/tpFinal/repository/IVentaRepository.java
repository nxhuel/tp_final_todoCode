package com.todocode.tpFinal.repository;

import com.todocode.tpFinal.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IVentaRepository extends JpaRepository<Venta, Long> {
}
