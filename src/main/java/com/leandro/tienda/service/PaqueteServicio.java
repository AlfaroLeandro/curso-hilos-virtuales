package com.leandro.tienda.service;

import com.leandro.tienda.cliente.ArticuloCliente;
import com.leandro.tienda.dto.ArticuloDetalleDTO;
import com.leandro.tienda.dto.ArticulosRelacionadosDTO;
import com.leandro.tienda.dto.PaqueteArticulosDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
@RequiredArgsConstructor
public class PaqueteServicio {

    private static final Logger log = LoggerFactory.getLogger(PaqueteServicio.class);
    private final ArticuloCliente articuloCliente;
    private final ExecutorService executor;

    public PaqueteArticulosDTO armarPaqueteArticulos(Long idArticulo) {
        try {
            var articuloFuture = CompletableFuture.supplyAsync(() -> obtenerDetalleArticulo(idArticulo), executor);
            var articulosRelacionadosFuture = CompletableFuture.supplyAsync(() -> articuloCliente.getRelacionados(idArticulo), executor)
                                                               .thenApply(ArticulosRelacionadosDTO::idsArticulos)
                                                               .thenApplyAsync(relacionados -> relacionados.parallelStream()
                                                                                                            .map(this::obtenerDetalleArticulo)
                                                                                                            .toList(), executor);

            return PaqueteArticulosDTO.builder()
                    .articulo(articuloFuture.get())
                    .articulosRelacionados(articulosRelacionadosFuture.get())
                    .build();
        } catch (Exception e) {
            log.error("Error al armar el paquete de articulos", e);
            throw new RuntimeException("Error al armar el paquete de articulos", e);
        }
    }

    private ArticuloDetalleDTO obtenerDetalleArticulo(Long idArticulo) {
        var articuloFuture = CompletableFuture.supplyAsync(() -> articuloCliente.getDetalle(idArticulo), executor);
        var articuloCategoriasFuture = CompletableFuture.supplyAsync(() -> articuloCliente.getCategorias(idArticulo), executor);
        var articuloStockFuture = CompletableFuture.supplyAsync(() -> articuloCliente.getStock(idArticulo), executor);
        var articuloVariacionesFuture = CompletableFuture.supplyAsync(() -> articuloCliente.getVariaciones(idArticulo), executor);
        var articuloReviewsFuture = CompletableFuture.supplyAsync(() -> articuloCliente.getReviews(idArticulo), executor);

        try {
            return ArticuloDetalleDTO.builder()
                    .articulo(articuloFuture.get())
                    .categorias(articuloCategoriasFuture.get())
                    .stock(articuloStockFuture.get())
                    .variaciones(articuloVariacionesFuture.get())
                    .review(articuloReviewsFuture.get())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
