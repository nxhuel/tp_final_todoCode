package com.todocode.tpFinal.service;

import com.todocode.tpFinal.model.Cliente;

import java.util.List;

public interface IClienteService {
    public List<Cliente> getClientes();

    public Cliente findByCliente(Long idCliente);

    public void createCliente(Cliente cliente);

    public void deleteCliente(Long idCliente);

    public void updateCliente(Long idCliente, String nuevoNombre, String nuevoApellido, String nuevoDni);
}
