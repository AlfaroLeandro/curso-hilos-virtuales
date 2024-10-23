package com.vinsguru.tripadvisor.dto2;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record EventoDTO(
    LocalDate fecha,
    String descripcion,
    String nombre
) {
}
