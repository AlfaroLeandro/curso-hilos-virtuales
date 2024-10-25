package com.leandro.tienda.dto;

import lombok.Builder;

@Builder
public record ArticuloDetalleDTO(

        ArticuloDTO articulo,
        ArticuloCategoriasDTO categorias,
        ArticuloStockDTO stock,
        ArticuloVariacionesDTO variaciones,
        ArticuloReview review
) {
}
