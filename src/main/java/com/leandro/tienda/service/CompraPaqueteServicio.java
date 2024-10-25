package com.leandro.tienda.service;

import com.leandro.tienda.cliente.ArticuloCliente;
import com.leandro.tienda.cliente.CompraCliente;
import com.leandro.tienda.dto.*;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
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

        var stocksCompra = articulosAComprar.stream().collect(Collectors.toMap(CompraRequestDTO::idArticulo,
                                                                               CompraRequestDTO::cantidad));
        boolean sotckInsuficiente = request.articulosAComprar().parallelStream()
                                                .map(CompraRequestDTO::idArticulo)
                                                .anyMatch(id -> Optional.ofNullable(articuloCliente.getStock(id))
                                                                        .map(ArticuloStockDTO::cantidad).orElseThrow() < stocksCompra.get(id));

        if(sotckInsuficiente)
            throw new RuntimeException("El stock de alguno de los articulos del paquete es insuficiente");

        // Realizar la compra de cada artículo
        List<CompraResponseDTO> comprasRealizadas = articulosAComprar.stream()
                .map(compraCliente::comprarArticulo)
                .collect(Collectors.toList());

        // Crear respuesta de compra del paquete
        PaqueteCompraResponse compraPaqueteResponse = PaqueteCompraResponse.builder()
                .idCompraPaquete(UUID.randomUUID())
                .articulosComprados(comprasRealizadas)
                .fechaEntrega(comprasRealizadas.stream()
                        .map(CompraResponseDTO::fechaEntrega)
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