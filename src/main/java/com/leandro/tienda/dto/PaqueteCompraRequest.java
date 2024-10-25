package com.leandro.tienda.dto;

import java.util.List;
import java.util.UUID;

public record PaqueteCompraRequest(
    List<CompraRequestDTO> articulosAComprar
) {
}
