package com.leandro.tienda.cliente;

import com.leandro.tienda.dto.CompraRequestDTO;
import com.leandro.tienda.dto.CompraResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@RequiredArgsConstructor
public class CompraCliente {
    private final RestClient client;

    // Cliente para comprar un artículo
    public CompraResponseDTO comprarArticulo(CompraRequestDTO compraRequestDTO) {
        return this.client.post()
                .uri("")
                .body(compraRequestDTO)
                .retrieve()
                .body(CompraResponseDTO.class);
    }

    // Cliente para consultar una compra específica por ID
    public CompraResponseDTO consultarCompra(UUID idCompra) {
        return this.client.get()
                .uri("/{idCompra}", idCompra)
                .retrieve()
                .body(CompraResponseDTO.class);
    }

}
