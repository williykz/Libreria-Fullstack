package com.example.servicio_pedidos.client;
import com.example.servicio_pedidos.dto.InventarioDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class InventarioClient {

    private final RestTemplate restTemplate;

    public InventarioClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public InventarioDTO obtenerInventarioPorLibroId(Long libroId) {
        try {
            return restTemplate.getForObject("http://localhost:8084/api/inventario/libro/" + libroId, InventarioDTO.class);
        } catch (RestClientException e) {
            return null;
        }
    }
}