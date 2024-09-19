package com.todocode.tpFinal.service;

import com.todocode.tpFinal.model.Producto;
import com.todocode.tpFinal.repository.IProductoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    private IProductoRepository iProductoRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto producto = new Producto();

    @BeforeEach
    void setUp() {
        producto.setCodigoProducto(1l);
        producto.setNombre("Marroc");
        producto.setMarca("Fort");
        producto.setCosto(500.0);
        producto.setCantidadDisponible(1000.0);
    }

    @Test
    void testCreateProducto() {
//        Given
        BDDMockito.lenient().when(iProductoRepository.findById(producto.getCodigoProducto()))
                        .thenReturn(Optional.empty());
        BDDMockito.given(iProductoRepository.save(producto)).willReturn(producto);
//        When
        Producto productoCreado = productoService.createProducto(producto);
//        Then
        Assertions.assertThat(productoCreado).isNotNull();
    }

    @Test
    void testGetProductos() {
//        Given
        Producto productoDos = new Producto();
        productoDos.setCodigoProducto(2l);
        productoDos.setNombre("Dos corazones");
        productoDos.setMarca("Fort");
        productoDos.setCosto(500.0);
        productoDos.setCantidadDisponible(1000.0);

        BDDMockito.given(iProductoRepository.findAll()).willReturn(List.of(producto, productoDos));
//        When
        List<Producto> listaProductos = productoService.getProductos();
//        Then
        Assertions.assertThat(listaProductos.size()).isEqualTo(2);
        Assertions.assertThat(listaProductos).contains(producto, productoDos);
    }

    @Test
    void testFindByProducto() {
//        Given
        BDDMockito.given(iProductoRepository.findById(1l)).willReturn(Optional.of(producto));
//        When
        Producto productoEncontrado = productoService.findByProducto(producto.getCodigoProducto());
//        Then
        Assertions.assertThat(productoEncontrado).isNotNull();
        Assertions.assertThat(productoEncontrado.getCodigoProducto()).isEqualTo(1l);
    }

    @Test
    void testUpdateProducto() {
//        Given
        producto.setNombre("Marroc grande");
        producto.setMarca("Fort");
        producto.setCosto(1000.0);
        producto.setCantidadDisponible(500.0);
        BDDMockito.given(iProductoRepository.findById(1l)).willReturn(Optional.of(producto));
        BDDMockito.given(iProductoRepository.save(producto)).willReturn(producto);
//        When
        Producto productoActualizado = productoService.updateProducto(producto.getCodigoProducto(),
                producto.getNombre(), producto.getMarca(), producto.getCosto(), producto.getCantidadDisponible());
//        Then
        Assertions.assertThat(productoActualizado.getCodigoProducto()).isEqualTo(1l);
        Assertions.assertThat(productoActualizado.getNombre()).isEqualTo("Marroc grande");
    }

    @Test
    void testDeleteProducto() {
//        Given
        long codigoProducto = 1l;
        BDDMockito.willDoNothing().given(iProductoRepository).deleteById(codigoProducto);
//        When
        productoService.deleteProducto(codigoProducto);
//        Then
        BDDMockito.verify(iProductoRepository, Mockito.times(1)).deleteById(codigoProducto);
    }

    @Test
    void testGetProductosConBajoStock() {
//        Given
        Producto productoBajoStock = new Producto();
        productoBajoStock.setCodigoProducto(2l);
        productoBajoStock.setNombre("Dos corazones");
        productoBajoStock.setMarca("Fort");
        productoBajoStock.setCosto(500.0);
        productoBajoStock.setCantidadDisponible(4.0);
        BDDMockito.given(iProductoRepository.findAll()).willReturn(List.of(productoBajoStock));
//        When
        List<Producto> productoTres = productoService.getProductosConBajoStock();
//        Then

        Assertions.assertThat(productoTres).isNotNull();
        Assertions.assertThat(productoTres.get(0).getCantidadDisponible()).isEqualTo(4.0);
    }
}
