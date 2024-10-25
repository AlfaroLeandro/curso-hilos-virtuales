package com.leandro.tienda.dto;

import lombok.Builder;

@Builder
public record VariacionDTO(
    String nombre,

    String descripcion
) {
}
