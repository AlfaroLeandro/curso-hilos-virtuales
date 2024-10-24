package com.vinsguru.tripadvisor.dto2;

import lombok.Builder;

import java.util.List;

@Builder
public record TransporteViajeDTO(
    List<TransporteDTO> alquilerVehiculos,
    List<TransporteDTO> transportesPublicos
) {}
