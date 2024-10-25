package com.leandro.tienda.cliente;

import com.leandro.tienda.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

@RequiredArgsConstructor
public class ArticuloCliente {
    private final RestClient client;

    // Cliente para obtener todos los artículos
    public ArticuloDTO getDetalle(Long idArticulo) {
        return this.client.get()
                .uri("/articulo/{id}/detalles", idArticulo)
                .retrieve()
                .body(ArticuloDTO.class);
    }

    // Cliente para obtener las categorías de un artículo
    public ArticuloCategoriasDTO getCategorias(Long idArticulo) {
        return this.client.get()
                .uri("/articulo/{id}/categorias", idArticulo)
                .retrieve()
                .body(ArticuloCategoriasDTO.class);
    }

    // Cliente para obtener el stock de un artículo
    public ArticuloStockDTO getStock(Long idArticulo) {
        return this.client.get()
                .uri("/articulo/{id}/stock", idArticulo)
                .retrieve()
                .body(ArticuloStockDTO.class);
    }

    // Cliente para obtener las variaciones de un artículo
    public ArticuloVariacionesDTO getVariaciones(Long idArticulo) {
        return this.client.get()
                .uri("/articulo/{id}/variaciones", idArticulo)
                .retrieve()
                .body(ArticuloVariacionesDTO.class);
    }

    // Cliente para obtener las reviews de un artículo
    public ArticuloReview getReviews(Long idArticulo) {
        return this.client.get()
                .uri("/articulo/{id}/reviews", idArticulo)
                .retrieve()
                .body(ArticuloReview.class);
    }

    public ArticulosRelacionadosDTO getRelacionados(Long idArticulo) {
        return this.client.get()
                .uri("/articulo/{id}/relacionados", idArticulo)
                .retrieve()
                .body(ArticulosRelacionadosDTO.class);
    }
}
