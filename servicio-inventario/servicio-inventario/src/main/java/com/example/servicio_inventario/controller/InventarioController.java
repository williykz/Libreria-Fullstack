package com.example.servicio_inventario.controller;

import com.example.servicio_inventario.model.Inventario;
import com.example.servicio_inventario.service.InventarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping
    public ResponseEntity<List<Inventario>> listarInventario() {
        List<Inventario> inventario = inventarioService.listarInventario();

        if (inventario.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(inventario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventario> buscarInventario(@PathVariable Long id) {
        Inventario inventario = inventarioService.buscarPorId(id);

        if (inventario == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(inventario);
    }

    @GetMapping("/libro/{libroId}")
    public ResponseEntity<Inventario> buscarPorLibroId(@PathVariable Long libroId) {
        Inventario inventario = inventarioService.buscarPorLibroId(libroId);

        if (inventario == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(inventario);
    }

    @PostMapping
    public ResponseEntity<Inventario> guardarInventario(@Valid @RequestBody Inventario inventario) {
        Inventario nuevoInventario = inventarioService.guardarInventario(inventario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoInventario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventario> actualizarInventario(@PathVariable Long id, @Valid @RequestBody Inventario inventario) {
        Inventario inventarioActualizado = inventarioService.actualizarInventario(id, inventario);

        if (inventarioActualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(inventarioActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarInventario(@PathVariable Long id) {
        boolean eliminado = inventarioService.eliminarInventario(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}