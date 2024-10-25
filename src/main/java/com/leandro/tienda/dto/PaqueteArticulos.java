package com.leandro.tienda.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PaqueteArticulos(
        ArticuloDetalleDTO articulo,
        List<ArticuloDetalleDTO> articulosRelacionados
) {
}
