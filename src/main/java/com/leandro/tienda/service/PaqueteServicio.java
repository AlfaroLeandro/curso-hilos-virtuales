package com.leandro.tienda.service;

import com.leandro.tienda.cliente.ArticuloCliente;
import com.leandro.tienda.dto.ArticuloDetalleDTO;
import com.leandro.tienda.dto.PaqueteArticulos;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
@RequiredArgsConstructor
public class PaqueteServicio {

    private static final Logger log = LoggerFactory.getLogger(PaqueteServicio.class);
    private final ArticuloCliente articuloCliente;
    private final ExecutorService executor;

    public PaqueteArticulos armarPaqueteArticulos(Long idArticulo) {
        var articuloDetallesFuture = CompletableFuture.supplyAsync(() -> articuloCliente.getArticulos(), executor);
        var articuloCategoriasFuture = CompletableFuture.supplyAsync(() -> articuloCliente.getCategorias(idArticulo), executor);
        var articuloStockFuture = CompletableFuture.supplyAsync(() -> articuloCliente.getStock(idArticulo), executor);
        var articuloVariacionesFuture = CompletableFuture.supplyAsync(() -> articuloCliente.getVariaciones(idArticulo), executor);
        var articuloReviewsFuture = CompletableFuture.supplyAsync(() -> articuloCliente.getReviews(idArticulo), executor);

        try {
            ArticuloDetalleDTO articuloDetalle = ArticuloDetalleDTO.builder()
                    .articulo(articuloDetallesFuture.get().stream().filter(a -> a.id().equals(idArticulo)).findFirst().orElse(null))
                    .categorias(articuloCategoriasFuture.get())
                    .stock(articuloStockFuture.get())
                    .variaciones(articuloVariacionesFuture.get())
                    .review(articuloReviewsFuture.get())
                    .build();

            List<ArticuloDetalleDTO> articulosRelacionados = articuloDetallesFuture.get();

            return PaqueteArticulos.builder()
                    .articulo(articuloDetalle)
                    .articulosRelacionados(articulosRelacionados)
                    .build();
        } catch (Exception e) {
            log.error("Error al armar el paquete de articulos", e);
            throw new RuntimeException("Error al armar el paquete de articulos", e);
        }
    }
}
