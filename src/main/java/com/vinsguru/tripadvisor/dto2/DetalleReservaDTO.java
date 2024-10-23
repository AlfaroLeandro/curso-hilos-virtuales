package com.vinsguru.tripadvisor.dto2;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record DetalleReservaDTO(
    Pais pais,
    LocalDate fecha,
    UUID idReserva,
    Float precio
) {
}
