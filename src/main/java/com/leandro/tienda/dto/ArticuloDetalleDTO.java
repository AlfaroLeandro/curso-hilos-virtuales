package com.leandro.tienda.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ArticuloDetalleDTO(

        ArticuloDTO articulo,
        List<String> categorias,
        ArticuloStockDTO stock,
        List<VariacionDTO> variaciones,
        ArticuloReview review
) {
}
