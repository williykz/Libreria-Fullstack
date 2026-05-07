package com.example.servicio_libros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.servicio_libros.model.Libro;
public interface LibroRepository extends JpaRepository<Libro, Long> {
}
