package com.todocode.tpFinal.controller;

import com.todocode.tpFinal.model.Cliente;
import com.todocode.tpFinal.service.IClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClienteController {

    @Autowired
    private IClienteService iClienteService;

    @GetMapping("/clientes")
    public List<Cliente> getClientes() {
        return iClienteService.getClientes();
    }

    @GetMapping("/clientes/{idCliente}")
    public Cliente getCliente(@PathVariable Long idCliente) {
        return iClienteService.findByCliente(idCliente);
    }

    @PostMapping("/clientes/crear")
    public ResponseEntity createCliente(@RequestBody @Valid Cliente cliente) {
        iClienteService.createCliente(cliente);
        return  new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/clientes/eliminar/{idCliente}")
    public ResponseEntity<String> deleteCliente(@PathVariable Long idCliente) {
        iClienteService.deleteCliente(idCliente);
        return new ResponseEntity<>("Cliente eliminado con éxito", HttpStatus.OK);
    }

    @PutMapping("/clientes/editar/{idCliente}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long idCliente,
                                 @RequestParam(required = false, value = "nombre") String nuevoNombre,
                                 @RequestParam(required = false, value = "apellido") String nuevoApellido,
                                 @RequestParam(required = false, value = "dni") String nuevoDni) {
        iClienteService.updateCliente(idCliente, nuevoNombre, nuevoApellido, nuevoDni);
        Cliente cliente = iClienteService.findByCliente(idCliente);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }
}
