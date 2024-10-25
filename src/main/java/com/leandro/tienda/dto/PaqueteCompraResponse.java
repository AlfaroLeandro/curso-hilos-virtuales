package com.leandro.tienda.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
public record PaqueteCompraResponse(
    UUID idCompraPaquete,
    List<CompraResponseDTO> articulosComprados,
    LocalDate fechaEntrega
) {
}

