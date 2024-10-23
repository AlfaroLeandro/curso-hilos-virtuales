package com.vinsguru.tripadvisor.dto2;

import lombok.Builder;

import java.util.List;

@Builder
public record TransporteAeropuertoDTO (
    List<TransporteDTO> rentaDeAutos,
    List<TransporteDTO> transportesPublicos
) {}
