package com.todocode.tpFinal.service;

import com.todocode.tpFinal.exception.cliente.ClientNotFoundException;
import com.todocode.tpFinal.model.Cliente;
import com.todocode.tpFinal.repository.IClienteRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private IClienteRepository iClienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente = new Cliente();

    @BeforeEach
    void setUp() {
        cliente.setIdCliente(2l);
        cliente.setNombre("Lucas");
        cliente.setApellido("Ramirez");
        cliente.setDni("42513514");
    }

    @Test
    void testCreateCliente() {
//        Given
        lenient().when(iClienteRepository.findById(cliente.getIdCliente())).
                thenReturn(Optional.empty());
        given(iClienteRepository.save(cliente)).willReturn(cliente);
//        When
        Cliente clienteCreado = clienteService.createCliente(cliente);
//        Then
        Assertions.assertThat(clienteCreado).isNotNull();
    }

    @Test
    void testCreateClienteWithThrowException() {
//        When
        when(iClienteRepository.save(any(Cliente.class)))
                .thenThrow(new ClientNotFoundException(cliente.getIdCliente()));
//        Then
        assertThrows(ClientNotFoundException.class, () -> {
            clienteService.createCliente(new Cliente());
        });
    }

    @Test
    void testGetClientes() {
//        Given
        Cliente clienteUno = new Cliente();
        clienteUno.setIdCliente(1l);
        clienteUno.setNombre("Julen");
        clienteUno.setApellido("Oliva");
        clienteUno.setDni("46315612");
        given(iClienteRepository.findAll()).willReturn(List.of(cliente, clienteUno));
//        When
        List<Cliente> listarClientes = clienteService.getClientes();
//        Then
        Assertions.assertThat(listarClientes).isNotNull();
        Assertions.assertThat(listarClientes.size()).isEqualTo(2);
    }

    @Test
    void testGetClientesVacia() {
//        Given
        Cliente clienteUno = new Cliente();
        clienteUno.setIdCliente(1l);
        clienteUno.setNombre("Julen");
        clienteUno.setApellido("Oliva");
        clienteUno.setDni("46315612");
        given(iClienteRepository.findAll()).willReturn(Collections.emptyList());
//        When
        List<Cliente> listarClientes = clienteService.getClientes();
//        Then
        Assertions.assertThat(listarClientes).isEmpty();
        Assertions.assertThat(listarClientes.size()).isEqualTo(0);
    }

    @Test
    void testFindByCliente() {
//        Given
        Cliente clienteUno = new Cliente();
        clienteUno.setIdCliente(1l);
        clienteUno.setNombre("Julen");
        clienteUno.setApellido("Oliva");
        clienteUno.setDni("46315612");
        given(iClienteRepository.findById(1l)).willReturn(Optional.of(clienteUno));
//        When
        Cliente clienteGuardado = clienteService.findByCliente(clienteUno.getIdCliente());
//        Then
        Assertions.assertThat(clienteGuardado).isNotNull();
        Assertions.assertThat(clienteGuardado.getIdCliente()).isEqualTo(1l);
    }

    @Test
    void testUpdateCliente() {
//        Given
        cliente.setNombre("Christian");
        cliente.setApellido("Cucitini");
        cliente.setDni("44513514");
        given(iClienteRepository.findById(2L)).willReturn(Optional.of(cliente));
        given(iClienteRepository.save(cliente)).willReturn(cliente);

//        When
        Cliente clienteActualizado = clienteService.updateCliente(cliente.getIdCliente(),
                cliente.getNombre(), cliente.getApellido(), cliente.getDni());
//        Then
        Assertions.assertThat(clienteActualizado.getIdCliente()).isEqualTo(2l);
        Assertions.assertThat(clienteActualizado.getNombre()).isEqualTo("Christian");
        Assertions.assertThat(clienteActualizado.getApellido()).isEqualTo("Cucitini");
        Assertions.assertThat(clienteActualizado.getDni()).isEqualTo("44513514");
    }

    @Test
    void testDeleteCliente() {
//        Given
        long clienteId = 1l;
        willDoNothing().given(iClienteRepository).deleteById(clienteId);
//        When
        clienteService.deleteCliente(clienteId);
//        Then
        verify(iClienteRepository, times(1)).deleteById(clienteId);
    }
}
