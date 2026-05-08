package com.example.servicio_pagos.client;


import com.example.servicio_pagos.dto.PedidoDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class PedidoClient {

    private final RestTemplate restTemplate;

    public PedidoClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PedidoDTO obtenerPedidoPorId(Long id) {
        try {
            return restTemplate.getForObject("http://localhost:8087/api/pedidos/" + id, PedidoDTO.class);
        } catch (RestClientException e) {
            return null;
        }
    }
}
