package com.example.servicio_pagos.controller;



import com.example.servicio_pagos.model.Pago;
import com.example.servicio_pagos.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagosController {

    private final PagoService pagoService;

    public PagosController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping
    public ResponseEntity<List<Pago>> listarPagos() {
        List<Pago> pagos = pagoService.listarPagos();

        if (pagos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> buscarPago(@PathVariable Long id) {
        Pago pago = pagoService.buscarPorId(id);

        if (pago == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(pago);
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<Pago>> buscarPorPedido(@PathVariable Long pedidoId) {
        List<Pago> pagos = pagoService.buscarPorPedidoId(pedidoId);

        if (pagos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/estado/{estadoPago}")
    public ResponseEntity<List<Pago>> buscarPorEstado(@PathVariable String estadoPago) {
        List<Pago> pagos = pagoService.buscarPorEstadoPago(estadoPago);

        if (pagos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/metodo/{metodoPago}")
    public ResponseEntity<List<Pago>> buscarPorMetodo(@PathVariable String metodoPago) {
        List<Pago> pagos = pagoService.buscarPorMetodoPago(metodoPago);

        if (pagos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/total")
    public ResponseEntity<Long> contarPagos() {
        return ResponseEntity.ok(pagoService.contarPagos());
    }

    @PostMapping
    public ResponseEntity<Pago> guardarPago(@Valid @RequestBody Pago pago) {
        Pago nuevoPago = pagoService.guardarPago(pago);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPago);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pago> actualizarPago(@PathVariable Long id, @Valid @RequestBody Pago pago) {
        Pago pagoActualizado = pagoService.actualizarPago(id, pago);

        if (pagoActualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(pagoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        boolean eliminado = pagoService.eliminarPago(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
