package com.example.servicio_pedidos.dto;

import lombok.Data;

@Data
public class InventarioDTO {

    private Long id;
    private Long libroId;
    private Integer stockActual;
    private Integer stockMinimo;
    private String ubicacion;
    private String estado;
}
