package com.vinsguru.tripadvisor.dto2;

import lombok.Builder;

@Builder
public record ClimaDTO(
    String condicion,
    Float temperatura
) {
}
