package com.vinsguru.tripadvisor.dto2;

import lombok.Builder;

@Builder
public record AlojamientoDTO(
    String nombre,
    TipoAlojamiento tipo,
    Float precio,
    Float rating
) {
}
