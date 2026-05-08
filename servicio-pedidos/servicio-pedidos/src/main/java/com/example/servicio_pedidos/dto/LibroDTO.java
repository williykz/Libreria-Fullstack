package com.example.servicio_pedidos.dto;

import lombok.Data;

@Data
public class LibroDTO {

    private Long id;
    private String titulo;
    private String autor;
    private String isbn;
    private String categoria;
    private Double precio;
    private Integer anioPublicacion;
    private Long editorialId;
    private Boolean activo;
}