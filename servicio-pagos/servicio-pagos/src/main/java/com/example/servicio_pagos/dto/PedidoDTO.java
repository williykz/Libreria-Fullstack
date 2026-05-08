package com.example.servicio_pagos.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PedidoDTO {

    private Long id;
    private Long clienteId;
    private LocalDate fechaPedido;
    private String estado;
    private Double total;
}