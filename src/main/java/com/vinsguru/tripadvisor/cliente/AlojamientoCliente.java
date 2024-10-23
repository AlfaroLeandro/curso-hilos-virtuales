package com.vinsguru.tripadvisor.cliente;

import com.vinsguru.tripadvisor.dto2.AlojamientoDTO;
import com.vinsguru.tripadvisor.dto2.Pais;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

@RequiredArgsConstructor
public class AlojamientoCliente {

    private final RestClient client;

    public List<AlojamientoDTO> getAlojamientos(Pais pais) {
        return this.client.get()
                          .uri("?pais=", pais)
                          .retrieve()
                          .body(new ParameterizedTypeReference<List<AlojamientoDTO>>() {
                          });
    }

}
