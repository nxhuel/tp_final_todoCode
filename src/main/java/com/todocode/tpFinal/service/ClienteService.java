package com.todocode.tpFinal.service;

import com.todocode.tpFinal.model.Cliente;
import com.todocode.tpFinal.repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    private IClienteRepository iClienteRepository;

    @Override
    public List<Cliente> getClientes() {
        return iClienteRepository.findAll();
    }
}
