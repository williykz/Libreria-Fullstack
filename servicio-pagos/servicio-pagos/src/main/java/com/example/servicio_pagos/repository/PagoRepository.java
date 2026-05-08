package com.example.servicio_pagos.repository;
import com.example.servicio_pagos.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByPedidoId(Long pedidoId);

    List<Pago> findByEstadoPago(String estadoPago);

    List<Pago> findByMetodoPago(String metodoPago);
}