package com.example.servicio_inventario.repository;

import com.example.servicio_inventario.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    Inventario findByLibroId(Long libroId);
}