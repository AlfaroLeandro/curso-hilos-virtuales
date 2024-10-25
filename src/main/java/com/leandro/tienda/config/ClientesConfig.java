package com.leandro.tienda.config;

import com.leandro.tienda.cliente.ArticuloCliente;
import com.leandro.tienda.cliente.CompraCliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.util.concurrent.Executors;

@Configuration
public class ClientesConfig {

    private static final Logger log = LoggerFactory.getLogger(ClientesConfig.class);

    @Value("${spring.threads.virtual.enabled}")
    private boolean isVirtualThreadEnabled;

    @Bean
    public CompraCliente compraCliente(@Value("${compra.service.url}") String baseUrl) {
        return new CompraCliente(buildRestClient(baseUrl));
    }

    @Bean
    public ArticuloCliente articuloCliente(@Value("${articulo.service.url}") String baseUrl) {
        return new ArticuloCliente(buildRestClient(baseUrl));
    }

    private RestClient buildRestClient(String baseUrl) {
        log.info("base url: {}", baseUrl);
        var builder = RestClient.builder().baseUrl(baseUrl);
        if (isVirtualThreadEnabled) {
            builder = builder.requestFactory(new JdkClientHttpRequestFactory(
                    HttpClient.newBuilder().executor(Executors.newVirtualThreadPerTaskExecutor()).build()
            ));
        }
        return builder.build();
    }

}
