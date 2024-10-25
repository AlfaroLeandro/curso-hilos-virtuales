package com.leandro.tienda.dto;

import lombok.Builder;

@Builder
public record CompraRequestDTO(
    Long idArticulo,
    Long cantidad
) {
}
