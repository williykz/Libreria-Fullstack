package com.example.servicio_clientes.controller;

import com.example.servicio_clientes.model.Clientes;
import com.example.servicio_clientes.service.ClientesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@RestController
@RequestMapping("/api/clientes")

public class ClienteController {


    private final ClientesService clientesService;

    public ClienteController(ClientesService clientesService) {
        this.clientesService = clientesService;
    }

    @GetMapping
    public ResponseEntity<List<Clientes>> listarClientes() {
        List<Clientes> clientes = clientesService.listarClientes();

        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(clientes);


    }

    @GetMapping("/{id}")
    public ResponseEntity<Clientes> buscarCliente(@PathVariable Long id) {
        Clientes cliente = clientesService.buscarPorId(id);

        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<Clientes> buscarPorRut(@PathVariable String rut) {
        Clientes cliente = clientesService.buscarPorRut(rut);

        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<Clientes> guardarCliente(@Valid @RequestBody Clientes cliente) {
        Clientes nuevoCliente = clientesService.guardarCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Clientes> actualizarCliente(@PathVariable Long id, @Valid @RequestBody Clientes cliente) {
        Clientes clienteActualizado = clientesService.actualizarCliente(id, cliente);

        if (clienteActualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(clienteActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        boolean eliminado = clientesService.eliminarCliente(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}