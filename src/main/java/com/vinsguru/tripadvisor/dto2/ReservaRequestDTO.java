package com.vinsguru.tripadvisor.dto2;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ReservaRequestDTO(
    Pais origen,
    Pais destino,
    LocalDate fechaSalida
) {
}
