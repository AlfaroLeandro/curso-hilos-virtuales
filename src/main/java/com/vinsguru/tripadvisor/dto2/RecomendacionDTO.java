package com.vinsguru.tripadvisor.dto2;

import lombok.Builder;

import java.util.List;

@Builder
public record RecomendacionDTO(
    List<String> restaurantes,
    List<String> paseos
) {
}
