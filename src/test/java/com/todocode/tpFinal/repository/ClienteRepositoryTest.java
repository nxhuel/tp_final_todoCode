package com.todocode.tpFinal.repository;

import com.todocode.tpFinal.model.Cliente;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class ClienteRepositoryTest {

    @Autowired
    private IClienteRepository iClienteRepository;

    Cliente cliente = new Cliente();

    @BeforeEach
    void setUp() {
        cliente.setNombre("Lucas");
        cliente.setApellido("Ramirez");
        cliente.setDni("42513514");
    }

    @Test
    void testCreateCliente() {
//        Given - Condicion previa
        Cliente clienteUno = new Cliente();
        clienteUno.setNombre("Juan");
        clienteUno.setApellido("Lopez");
        clienteUno.setDni("45513514");
//        When - El comportamiento que vamos a proabar
        Cliente clienteCreado = iClienteRepository.save(clienteUno);
//        Then - Salida
        Assertions.assertThat(clienteCreado).isNotNull();
        Assertions.assertThat(clienteCreado.getIdCliente()).isGreaterThan(0);
    }

    @Test
    void testGetClientes() {
//        Given
        Cliente clienteUno = new Cliente();
        clienteUno.setNombre("Julen");
        clienteUno.setApellido("Oliva");
        clienteUno.setDni("46315612");
        iClienteRepository.save(clienteUno);
        iClienteRepository.save(cliente);
//        When
        List<Cliente> listaClientes = iClienteRepository.findAll();
//        Then
        Assertions.assertThat(listaClientes).isNotNull();
        Assertions.assertThat(listaClientes.size()).isEqualTo(2);
    }

    @Test
    void testFindByCliente() {
//        Given
        iClienteRepository.save(cliente);
//        When
        Cliente traerCliente = iClienteRepository.findById(cliente.getIdCliente()).get();
//        Then
        Assertions.assertThat(traerCliente).isNotNull();
        Assertions.assertThat(traerCliente.getIdCliente()).isEqualTo(cliente.getIdCliente());
    }

    @Test
    void testUpdateCliente() {
//        Given
        iClienteRepository.save(cliente);
//        When
        Cliente traerCliente = iClienteRepository.findById(cliente.getIdCliente()).get();
        traerCliente.setNombre("Christian");
        traerCliente.setApellido("Cucitini");
        traerCliente.setDni("44513514");
        Cliente clienteGuardado = iClienteRepository.save(traerCliente);
//        Then
        Assertions.assertThat(clienteGuardado.getNombre()).isEqualTo("Christian");
        Assertions.assertThat(clienteGuardado.getApellido()).isEqualTo("Cucitini");
        Assertions.assertThat(clienteGuardado.getDni()).isEqualTo("44513514");
    }

    @Test
    void testDeleteCliente() {
//        Given
        iClienteRepository.save(cliente);
//        When
        iClienteRepository.deleteById(cliente.getIdCliente());
        Optional<Cliente> clienteOptional = iClienteRepository.findById(cliente.getIdCliente());
//        Then
        Assertions.assertThat(clienteOptional).isEmpty();
    }
}
