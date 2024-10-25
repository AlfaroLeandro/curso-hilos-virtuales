package com.leandro.tienda.dto;

import lombok.Builder;

import java.util.List;

@Builder
public class ArticulosRelacionadosDTO {
    List<Long> idsArticulos;
}
