package com.example.servicio_inventario.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long libroId;

    @NotNull
    @Min(0)
    private Integer stockActual;

    @NotNull
    @Min(0)
    private Integer stockMinimo;

    private String ubicacion;

    private String estado;
}
