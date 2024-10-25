package com.leandro.tienda.dto;

import java.util.List;
import java.util.UUID;

public record PaqueteCompraRequest(
    UUID idPaquete,
    List<CompraRequestDTO> articulosAComprar
) {
}
