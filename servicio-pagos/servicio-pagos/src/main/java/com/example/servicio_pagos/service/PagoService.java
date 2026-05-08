package com.example.servicio_pagos.service;


import com.example.servicio_pagos.client.PedidoClient;
import com.example.servicio_pagos.dto.PedidoDTO;
import com.example.servicio_pagos.model.Pago;
import com.example.servicio_pagos.repository.PagoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final PedidoClient pedidoClient;

    public PagoService(PagoRepository pagoRepository, PedidoClient pedidoClient) {
        this.pagoRepository = pagoRepository;
        this.pedidoClient = pedidoClient;
    }

    public List<Pago> listarPagos() {
        return pagoRepository.findAll();
    }

    public Pago buscarPorId(Long id) {
        return pagoRepository.findById(id).orElse(null);
    }

    public List<Pago> buscarPorPedidoId(Long pedidoId) {
        return pagoRepository.findByPedidoId(pedidoId);
    }

    public List<Pago> buscarPorEstadoPago(String estadoPago) {
        return pagoRepository.findByEstadoPago(estadoPago);
    }

    public List<Pago> buscarPorMetodoPago(String metodoPago) {
        return pagoRepository.findByMetodoPago(metodoPago);
    }

    public Pago guardarPago(Pago pago) {
        PedidoDTO pedido = pedidoClient.obtenerPedidoPorId(pago.getPedidoId());

        if (pedido == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El pedido no existe");
        }

        if (pago.getMonto() > pedido.getTotal()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El monto no puede ser mayor al total del pedido");
        }

        if (pago.getFechaPago() == null) {
            pago.setFechaPago(LocalDate.now());
        }

        if (pago.getEstadoPago() == null) {
            pago.setEstadoPago("PENDIENTE");
        }

        return pagoRepository.save(pago);
    }

    public Pago actualizarPago(Long id, Pago pago) {
        Pago pagoExistente = buscarPorId(id);

        if (pagoExistente == null) {
            return null;
        }

        PedidoDTO pedido = pedidoClient.obtenerPedidoPorId(pago.getPedidoId());

        if (pedido == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El pedido no existe");
        }

        if (pago.getMonto() > pedido.getTotal()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El monto no puede ser mayor al total del pedido");
        }

        pagoExistente.setPedidoId(pago.getPedidoId());
        pagoExistente.setMonto(pago.getMonto());
        pagoExistente.setMetodoPago(pago.getMetodoPago());
        pagoExistente.setEstadoPago(pago.getEstadoPago());
        pagoExistente.setFechaPago(pago.getFechaPago());

        return pagoRepository.save(pagoExistente);
    }

    public boolean eliminarPago(Long id) {
        Pago pago = buscarPorId(id);

        if (pago == null) {
            return false;
        }

        pagoRepository.delete(pago);
        return true;
    }

    public long contarPagos() {
        return pagoRepository.count();
    }
}