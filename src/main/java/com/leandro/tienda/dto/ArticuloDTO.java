package com.leandro.tienda.dto;

import lombok.Builder;

@Builder
public record ArticuloDTO(

        Long id,

        String nombre,

        Float precio

) {
}
