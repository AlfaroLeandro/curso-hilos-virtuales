package com.vinsguru.tripadvisor.dto2;

import lombok.Builder;

@Builder
public record TransporteDTO(

    String agencia,
    Float precio

) {
}
