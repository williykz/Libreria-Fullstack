package com.example.servicio_clientes.repository;

import com.example.servicio_clientes.model.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Clientes, Long> {
    Clientes findByRut(String rut);
}
