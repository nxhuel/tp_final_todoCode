package com.todocode.tpFinal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todocode.tpFinal.model.Producto;
import com.todocode.tpFinal.service.IProductoService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = ProductoController.class)
public class ProductoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IProductoService iProductoService;

    @Test
    void testCreateProducto() throws Exception {
//        Given
        Producto producto = new Producto();
        producto.setCodigoProducto(1l);
        producto.setNombre("Marroc");
        producto.setMarca("Fort");
        producto.setCosto(500.0);
        producto.setCantidadDisponible(1000.0);
        BDDMockito.given(iProductoService.createProducto(ArgumentMatchers.any(Producto.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));
//        When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/productos/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)));

//        Then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", CoreMatchers.is(producto.getNombre())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.marca", CoreMatchers.is(producto.getMarca())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.costo", CoreMatchers.is(producto.getCosto())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cantidadDisponible", CoreMatchers.is(producto.getCantidadDisponible())));
    }

    @Test
    void testGetProductos() throws Exception{
//        Given
        List<Producto> listaProductos = new ArrayList<>();
        Producto producto = new Producto();
        producto.setCodigoProducto(1l);
        producto.setNombre("Marroc");
        producto.setMarca("Fort");
        producto.setCosto(500.0);
        producto.setCantidadDisponible(1000.0);
        listaProductos.add(producto);

        Producto productoDos = new Producto();
        productoDos.setCodigoProducto(2l);
        productoDos.setNombre("Dos corazones");
        productoDos.setMarca("Fort");
        productoDos.setCosto(500.0);
        productoDos.setCantidadDisponible(1000.0);
        listaProductos.add(productoDos);

        Producto productoTres = new Producto();
        productoTres.setCodigoProducto(3l);
        productoTres.setNombre("Barra");
        productoTres.setMarca("Fort");
        productoTres.setCosto(500.0);
        productoTres.setCantidadDisponible(1000.0);
        listaProductos.add(productoTres);

        BDDMockito.given(iProductoService.getProductos()).willReturn(listaProductos);
//        When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(listaProductos)));
//        Then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(listaProductos.size())));
    }

    @Test
    void testUpdateProductos() throws Exception{
//        Given
        long codigoProducto = 1l;
        Producto producto = new Producto();
        producto.setNombre("Marroc");
        producto.setMarca("Fort");
        producto.setCosto(500.0);
        producto.setCantidadDisponible(1000.0);

        Producto productoActualizado = new Producto();
        productoActualizado.setNombre("Marroc grande");
        producto.setMarca("Fort");
        producto.setCosto(500.0);
        producto.setCantidadDisponible(1000.0);

        BDDMockito.given(iProductoService.findByProducto(codigoProducto)).willReturn(productoActualizado);

        BDDMockito.given(iProductoService.updateProducto(codigoProducto
                , productoActualizado.getNombre(), productoActualizado.getMarca(), productoActualizado.getCosto(), productoActualizado.getCantidadDisponible()))
                .willAnswer((invocation) -> invocation.getArgument(0));
//        When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .put("/productos/editar/{codigoProducto}", codigoProducto)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoActualizado)));
//        Then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", CoreMatchers.is(productoActualizado.getNombre())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.marca", CoreMatchers.is(productoActualizado.getMarca())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.costo", CoreMatchers.is(productoActualizado.getCosto())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cantidadDisponible", CoreMatchers.is(productoActualizado.getCantidadDisponible())));
    }

    @Test
    void testDeleteProducto() throws Exception {
//        Given
        long codigoProducto = 1l;
        BDDMockito.willDoNothing().given(iProductoService).deleteProducto(codigoProducto);
//        When
        ResultActions respone = mockMvc.perform(MockMvcRequestBuilders.delete("/productos/eliminar/{codigoProducto}", codigoProducto));
//        Then
        respone.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
