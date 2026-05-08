package com.example.servicio_pedidos.client;

import com.example.servicio_pedidos.dto.LibroDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class LibroClient {

    private final RestTemplate restTemplate;

    public LibroClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public LibroDTO obtenerLibroPorId(Long id) {
        try {
            return restTemplate.getForObject("http://localhost:8083/api/libros/" + id, LibroDTO.class);
        } catch (RestClientException e) {
            return null;
        }
    }
}