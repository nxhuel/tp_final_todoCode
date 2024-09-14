package com.todocode.tpFinal.service;

import com.todocode.tpFinal.exception.ClienteNotFoundException;
import com.todocode.tpFinal.exception.ClienteNoDataFoundException;
import com.todocode.tpFinal.model.Cliente;
import com.todocode.tpFinal.repository.IClienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    private IClienteRepository iClienteRepository;

    @Override
    public List<Cliente> getClientes() {
        var clientes = (List<Cliente>) iClienteRepository.findAll();

        if (clientes.isEmpty()) {
            throw new ClienteNoDataFoundException();
        }

        return clientes;
    }

    @Override
    public Cliente findByCliente(Long idCliente) {
        return iClienteRepository.findById(idCliente).orElseThrow(() -> new ClienteNotFoundException(idCliente));
    }

    @Override
    public void createCliente(Cliente cliente) {
        iClienteRepository.save(cliente);
    }

    @Override
    public void deleteCliente(Long idCliente) {
        iClienteRepository.deleteById(idCliente);
    }

    @Override
    public void updateCliente(Long idCliente, String nuevoNombre, String nuevoApellido, String nuevoDni) {
        Cliente nuevoCliente = this.findByCliente(idCliente);

        if (nuevoCliente == null) {
            throw new ClienteNotFoundException(idCliente);
        }

        nuevoCliente.setNombre(nuevoNombre);
        nuevoCliente.setApellido(nuevoApellido);
        nuevoCliente.setDni(nuevoDni);

        this.createCliente(nuevoCliente);
    }
}
