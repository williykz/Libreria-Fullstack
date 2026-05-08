package com.example.servicio_pedidos.repository;

import com.example.servicio_pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByClienteId(Long clienteId);

    List<Pedido> findByEstado(String estado);

    List<Pedido> id(Long id);
}
