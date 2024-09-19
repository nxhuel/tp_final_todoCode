package com.todocode.tpFinal.repository;

import com.todocode.tpFinal.model.Producto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class ProductoRepositoryTest {

    @Autowired
    private IProductoRepository iProductoRepository;

    Producto producto = new Producto();

    @BeforeEach
    void setUp() {
        producto.setCodigoProducto(1l);
        producto.setNombre("Marroc");
        producto.setMarca("Fort");
        producto.setCosto(500.0);
        producto.setCantidadDisponible(1000.0);
    }

    @Test
    void testCreateProduct() {
//        Given
        Producto productoDos = new Producto();
        productoDos.setCodigoProducto(2l);
        productoDos.setNombre("Papas fritas");
        productoDos.setMarca("Lays");
        productoDos.setCosto(100.0);
        productoDos.setCantidadDisponible(3000.0);
//        When
        Producto productoCreado = iProductoRepository.save(productoDos);
//        Then
        Assertions.assertThat(productoCreado).isNotNull();
        Assertions.assertThat(productoCreado.getCodigoProducto()).isGreaterThan(0);
    }

    @Test
    void testGetProducts() {
//        Given
        Producto productoDos = new Producto();
        productoDos.setCodigoProducto(2l);
        productoDos.setNombre("Papas fritas");
        productoDos.setMarca("Lays");
        productoDos.setCosto(100.0);
        productoDos.setCantidadDisponible(3000.0);

        iProductoRepository.save(producto);
        iProductoRepository.save(productoDos);
//        When
        List<Producto> listaProductos = iProductoRepository.findAll();
//        Then
        Assertions.assertThat(listaProductos).isNotNull();
        Assertions.assertThat(listaProductos.size()).isEqualTo(2);
    }

    @Test
    void testFindByProducto() {
//        Given
        iProductoRepository.save(producto);
//        When
        Producto productoEncontrado = iProductoRepository.findById(producto.getCodigoProducto()).get();
//        Then
        Assertions.assertThat(productoEncontrado).isNotNull();
        Assertions.assertThat(productoEncontrado.getCodigoProducto()).isEqualTo(producto.getCodigoProducto());
    }

    @Test
    void testUpdateProducto() {
//        Given
        iProductoRepository.save(producto);

        Producto traerProducto = iProductoRepository.findById(producto.getCodigoProducto()).get();
        traerProducto.setNombre("Dos corazones");
        traerProducto.setMarca("Fort");
        traerProducto.setCosto(500.0);
        traerProducto.setCantidadDisponible(1000.0);
//        When
        Producto productoActualizado = iProductoRepository.save(traerProducto);
//        Then
        Assertions.assertThat(productoActualizado).isNotNull();
        Assertions.assertThat(productoActualizado.getNombre()).isEqualTo("Dos corazones");
    }

    @Test
    void testDeleteProducto() {
//        Given
        iProductoRepository.save(producto);
//        When
        iProductoRepository.deleteById(producto.getCodigoProducto());
        Optional<Producto> productoOptional =  iProductoRepository.findById(producto.getCodigoProducto());
//        Then
        Assertions.assertThat(productoOptional).isEmpty();
    }
}
