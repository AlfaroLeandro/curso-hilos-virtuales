package com.leandro.tienda.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ArticuloReview(
        LocalDate fecha,
        String comentario,
        Float rating
) {
}
