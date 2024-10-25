package com.leandro.tienda.dto;

import lombok.Builder;

@Builder
public record ArticuloStockDTO(
        Long cantidad
) {
}
