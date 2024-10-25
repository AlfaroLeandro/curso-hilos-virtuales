package com.leandro.tienda.controlador;


import com.leandro.tienda.dto.PaqueteCompraRequest;
import com.leandro.tienda.dto.PaqueteCompraResponse;
import com.leandro.tienda.service.CompraPaqueteServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/paquete")
@RequiredArgsConstructor
public class PaqueteControlador {

    private final CompraPaqueteServicio compraPaqueteServicio;

    @PostMapping("/compra")
    public ResponseEntity<PaqueteCompraResponse> comprarPaquete(@RequestBody PaqueteCompraRequest request) {
        try {
            PaqueteCompraResponse response = compraPaqueteServicio.comprarPaquete(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/compra/{idCompraPaquete}")
    public ResponseEntity<PaqueteCompraResponse> consultarCompraPaquete(@PathVariable UUID idCompraPaquete) {
        try {
            PaqueteCompraResponse response = compraPaqueteServicio.consultarCompraPaquete(idCompraPaquete);
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
