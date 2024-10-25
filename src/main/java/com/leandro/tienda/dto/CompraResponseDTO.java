package com.leandro.tienda.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record CompraResponseDTO(

    UUID idCompra,
    Long articulo,
    Long cantidadComprada,
    LocalDate fechaEntrega

) {
}
