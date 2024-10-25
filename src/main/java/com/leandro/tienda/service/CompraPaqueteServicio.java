package com.leandro.tienda.service;

import com.leandro.tienda.cliente.ArticuloCliente;
import com.leandro.tienda.cliente.CompraCliente;
import com.leandro.tienda.dto.ArticuloDetalleDTO;
import com.leandro.tienda.dto.CompraRequestDTO;
import com.leandro.tienda.dto.PaqueteCompraRequest;
import com.leandro.tienda.dto.PaqueteCompraResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompraPaqueteServicio {

    private static final Logger log = LoggerFactory.getLogger(CompraPaqueteServicio.class);
    private final ArticuloCliente articuloCliente;
    private final CompraCliente compraCliente;
    private static final List<PaqueteCompraResponse> comprasPaquetesRealizadas = new ArrayList<>();

    public PaqueteCompraResponse comprarPaquete(PaqueteCompraRequest request) {
        List<CompraRequestDTO> articulosAComprar = request.articulosAComprar();

        // Obtener detalles de todos los artículos solicitados
        List<ArticuloDetalleDTO> detallesArticulos = articulosAComprar.stream()
                .map(articulo -> articuloCliente.getArticulos().stream()
                        .filter(a -> a.id().equals(articulo.idArticulo()))
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("Artículo no encontrado: " + articulo.idArticulo()))
                )
                .collect(Collectors.toList());

        // Realizar la compra de cada artículo
        List<PaqueteCompraResponse> comprasRealizadas = articulosAComprar.stream()
                .map(compraCliente::comprarArticulo)
                .collect(Collectors.toList());

        // Crear respuesta de compra del paquete
        PaqueteCompraResponse compraPaqueteResponse = PaqueteCompraResponse.builder()
                .idCompraPaquete(UUID.randomUUID())
                .articulosComprados(comprasRealizadas)
                .fechaEntrega(comprasRealizadas.stream()
                        .map(PaqueteCompraRequest::fechaEntrega)
                        .max(Comparator.naturalOrder())
                        .orElse(LocalDate.now().plusDays(3)))
                .build();

        // Almacenar la compra del paquete en la lista estática
        comprasPaquetesRealizadas.add(compraPaqueteResponse);

        return compraPaqueteResponse;
    }

    public PaqueteCompraResponse consultarCompraPaquete(UUID idCompraPaquete) {
        return comprasPaquetesRealizadas.stream()
                .filter(compra -> compra.idCompraPaquete().equals(idCompraPaquete))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Compra de paquete no encontrada: " + idCompraPaquete));
    }
}