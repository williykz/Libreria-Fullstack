package com.example.servicio_pedidos.repository;

import com.example.servicio_pedidos.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetallePedidoRepository  extends JpaRepository<DetallePedido, Long> {

    List<DetallePedido> findByLibroId(Long libroId);
}
