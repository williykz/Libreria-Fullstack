package com.example.servicio_clientes.service;

import com.example.servicio_clientes.model.Clientes;
import com.example.servicio_clientes.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClientesService {
    private final ClienteRepository clientesRepository;

    public ClientesService(ClienteRepository clientesRepository) {
        this.clientesRepository = clientesRepository;
    }

    public List<Clientes> listarClientes() {
        return clientesRepository.findAll();
    }

    public Clientes buscarPorId(Long id) {
        return clientesRepository.findById(id).orElse(null);
    }

    public Clientes buscarPorRut(String rut) {
        return clientesRepository.findByRut(rut);
    }

    public Clientes guardarCliente(Clientes cliente) {
        return clientesRepository.save(cliente);
    }

    public Clientes actualizarCliente(Long id, Clientes cliente) {
        Clientes clienteExistente = buscarPorId(id);

        if (clienteExistente == null) {
            return null;
        }

        clienteExistente.setRut(cliente.getRut());
        clienteExistente.setNombre(cliente.getNombre());
        clienteExistente.setApellido(cliente.getApellido());
        clienteExistente.setCorreo(cliente.getCorreo());
        clienteExistente.setTelefono(cliente.getTelefono());
        clienteExistente.setDireccion(cliente.getDireccion());
        clienteExistente.setActivo(cliente.getActivo());

        return clientesRepository.save(clienteExistente);
    }

    public boolean eliminarCliente(Long id) {
        Clientes cliente = buscarPorId(id);

        if (cliente == null) {
            return false;
        }

        clientesRepository.delete(cliente);
        return true;
    }
}
