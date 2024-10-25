package com.leandro.tienda.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record PaqueteCompraResponse(
    UUID idCompraPaquete,
    List<CompraResponseDTO> articulosComprados,
    LocalDate fechaEntrega
) {
}

