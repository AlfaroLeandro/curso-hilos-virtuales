package com.leandro.tienda.dto;

import java.util.List;

public record PaqueteArticulos(
        ArticuloDetalleDTO articulo,
        List<ArticuloDetalleDTO> articulosRelacionados
) {
}
