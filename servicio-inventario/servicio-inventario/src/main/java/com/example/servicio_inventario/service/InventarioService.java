package com.example.servicio_inventario.service;

import com.example.servicio_inventario.model.Inventario;
import com.example.servicio_inventario.repository.InventarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioService {

    private final InventarioRepository inventarioRepository;

    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    public List<Inventario> listarInventario() {
        return inventarioRepository.findAll();
    }

    public Inventario buscarPorId(Long id) {
        return inventarioRepository.findById(id).orElse(null);
    }

    public Inventario buscarPorLibroId(Long libroId) {
        return inventarioRepository.findByLibroId(libroId);
    }

    public Inventario guardarInventario(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    public Inventario actualizarInventario(Long id, Inventario inventario) {
        Inventario inventarioExistente = buscarPorId(id);

        if (inventarioExistente == null) {
            return null;
        }

        inventarioExistente.setLibroId(inventario.getLibroId());
        inventarioExistente.setStockActual(inventario.getStockActual());
        inventarioExistente.setStockMinimo(inventario.getStockMinimo());
        inventarioExistente.setUbicacion(inventario.getUbicacion());
        inventarioExistente.setEstado(inventario.getEstado());

        return inventarioRepository.save(inventarioExistente);
    }

    public boolean eliminarInventario(Long id) {
        Inventario inventario = buscarPorId(id);

        if (inventario == null) {
            return false;
        }

        inventarioRepository.delete(inventario);
        return true;
    }
}