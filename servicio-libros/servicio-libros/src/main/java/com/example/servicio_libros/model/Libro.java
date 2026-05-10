package com.example.servicio_libros.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String titulo;

    @NotBlank
    private String autor;

    @NotBlank
    @Column(unique = true)
    private String isbn;

    @NotBlank
    private String categoria;

    @NotNull
    @Positive
    private Double precio;

    @NotNull
    private Integer anioPublicacion;

    private Long editorialId;

    private Boolean activo = true;
}
