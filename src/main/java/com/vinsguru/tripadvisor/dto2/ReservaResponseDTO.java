package com.vinsguru.tripadvisor.dto2;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record ReservaResponseDTO(

    UUID idReserva,
    Pais origen,
    Pais destino,
    LocalDate fechaSalida

) {
}
