package com.example.servicio_pagos.service;


import com.example.servicio_pagos.client.PedidoClient;
import com.example.servicio_pagos.dto.PedidoDTO;
import com.example.servicio_pagos.model.Pago;
import com.example.servicio_pagos.repository.PagoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

@Service
public class PagoService {
    private static final Logger logger = LoggerFactory.getLogger(PagoService.class);

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
        logger.info("Iniciando registro de pago para pedido ID: {}", pago.getPedidoId());

        PedidoDTO pedido = pedidoClient.obtenerPedidoPorId(pago.getPedidoId());

        if (pedido == null) {
            logger.warn("No se encontró el pedido ID: {}", pago.getPedidoId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El pedido no existe");
        }

        if (pago.getMonto() > pedido.getTotal()) {
            logger.warn("Monto inválido. Monto pago: {}, total pedido: {}", pago.getMonto(), pedido.getTotal());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El monto no puede ser mayor al total del pedido");
        }

        if (pago.getFechaPago() == null) {
            pago.setFechaPago(LocalDate.now());
        }

        if (pago.getEstadoPago() == null) {
            pago.setEstadoPago("PENDIENTE");
        }

        Pago pagoGuardado = pagoRepository.save(pago);

        logger.info("Pago registrado correctamente con ID: {}", pagoGuardado.getId());

        return pagoGuardado;
    }

    public Pago actualizarPago(Long id, Pago pago) {
        logger.info("Iniciando actualización de pago ID: {}", id);

        Pago pagoExistente = buscarPorId(id);

        if (pagoExistente == null) {
            logger.warn("No se encontró el pago ID: {}", id);
            return null;
        }

        PedidoDTO pedido = pedidoClient.obtenerPedidoPorId(pago.getPedidoId());

        if (pedido == null) {
            logger.warn("No se encontró el pedido ID: {}", pago.getPedidoId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El pedido no existe");
        }

        if (pago.getMonto() > pedido.getTotal()) {
            logger.warn("Monto inválido. Monto pago: {}, total pedido: {}", pago.getMonto(), pedido.getTotal());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El monto no puede ser mayor al total del pedido");
        }

        pagoExistente.setPedidoId(pago.getPedidoId());
        pagoExistente.setMonto(pago.getMonto());
        pagoExistente.setMetodoPago(pago.getMetodoPago());
        pagoExistente.setEstadoPago(pago.getEstadoPago());
        pagoExistente.setFechaPago(pago.getFechaPago());

        Pago pagoActualizado = pagoRepository.save(pagoExistente);

        logger.info("Pago actualizado correctamente con ID: {}", pagoActualizado.getId());

        return pagoActualizado;
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