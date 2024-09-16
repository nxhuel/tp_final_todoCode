package com.todocode.tpFinal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todocode.tpFinal.exception.cliente.ClientNotFoundException;
import com.todocode.tpFinal.model.Cliente;
import com.todocode.tpFinal.service.IClienteService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
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

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(controllers = ClienteController.class)
public class ClienteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IClienteService iClienteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateCliente() throws Exception {
//        Given
        Cliente clienteUno = new Cliente();
        clienteUno.setIdCliente(1l);
        clienteUno.setNombre("Julen");
        clienteUno.setApellido("Oliva");
        clienteUno.setDni("46315612");
        BDDMockito.given(iClienteService.createCliente(any(Cliente.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));
//        When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/clientes/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteUno)));
//        Then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", CoreMatchers.is(clienteUno.getNombre())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apellido", CoreMatchers.is(clienteUno.getApellido())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dni", CoreMatchers.is(clienteUno.getDni())));
    }

    @Test
    void testGetCliente() throws Exception {
//        Given
        List<Cliente> listaClientes = new ArrayList<>();
        Cliente clienteUno = new Cliente();
        clienteUno.setIdCliente(1l);
        clienteUno.setNombre("Julen");
        clienteUno.setApellido("Oliva");
        clienteUno.setDni("46315612");
        listaClientes.add(clienteUno);

        Cliente clienteDos = new Cliente();
        clienteDos.setIdCliente(2l);
        clienteDos.setNombre("Gabriel");
        clienteDos.setApellido("Ramirez");
        clienteDos.setDni("42512514");
        listaClientes.add(clienteDos);

        Cliente clienteTres = new Cliente();
        clienteTres.setIdCliente(3l);
        clienteTres.setNombre("Biaggio");
        clienteTres.setApellido("Ramirez");
        clienteTres.setDni("45315612");
        listaClientes.add(clienteTres);

        BDDMockito.given(iClienteService.getClientes()).willReturn(listaClientes);
//        When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(listaClientes)));
//        Then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(listaClientes.size())));
    }

    @Test
    void testFindByCliente() throws Exception {
//        Given
        long clienteId = 1l;
        Cliente clienteUno = new Cliente();
        clienteUno.setNombre("Julen");
        clienteUno.setApellido("Oliva");
        clienteUno.setDni("46315612");
        BDDMockito.given(iClienteService.findByCliente(clienteId)).willReturn(clienteUno);
//        When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/clientes/{idCliente}", clienteId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteUno)));
//        Then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", CoreMatchers.is(clienteUno.getNombre())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apellido", CoreMatchers.is(clienteUno.getApellido())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dni", CoreMatchers.is(clienteUno.getDni())));
    }

    @Test
    void testFindByClienteNoEncontrado() throws Exception {
//        Given
        long clienteId = 1l;
        Cliente clienteUno = new Cliente();
        clienteUno.setNombre("Julen");
        clienteUno.setApellido("Oliva");
        clienteUno.setDni("46315612");
        BDDMockito.given(iClienteService.findByCliente(clienteId)).
                willThrow(new ClientNotFoundException(clienteId));
//        When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/clientes/{idCliente}", clienteId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteUno)));
//        Then
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testActualizarCliente() throws Exception {
//        Given
        long clienteId = 1l;
        Cliente clienteUno = new Cliente();
        clienteUno.setNombre("Julen");
        clienteUno.setApellido("Oliva");
        clienteUno.setDni("46315612");

        Cliente clienteUnoActualizado = new Cliente();
        clienteUnoActualizado.setNombre("Raul");
        clienteUnoActualizado.setApellido("Rodriguez");
        clienteUnoActualizado.setDni("44315612");

        BDDMockito.given(iClienteService.findByCliente(clienteId)).willReturn(clienteUnoActualizado);

        BDDMockito.given(iClienteService.updateCliente(clienteId,
                        clienteUnoActualizado.getNombre(), clienteUnoActualizado.getApellido(), clienteUnoActualizado.getDni()))
                .willAnswer((invocation) -> invocation.getArgument(0));
//        When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .put("/clientes/editar/{idCliente}", clienteId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteUnoActualizado)));
//        Then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", CoreMatchers.is(clienteUnoActualizado.getNombre())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apellido", CoreMatchers.is(clienteUnoActualizado.getApellido())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dni", CoreMatchers.is(clienteUnoActualizado.getDni())));
    }

    @Test
    void testDeleteCliente() throws Exception {
//        Given
        long clienteId = 1l;
        BDDMockito.willDoNothing().given(iClienteService).deleteCliente(clienteId);
//        When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/clientes/eliminar/{idCliente}", clienteId));
//        Then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }
}
