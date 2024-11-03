package sec2executors.servicioexterno;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ArticuloCliente {

    private static final Logger log = LoggerFactory.getLogger(ArticuloCliente.class);
    private static final String PRODUCTO_URI = "http://localhost:8081/productos/product/%d";
    private static final String PRODUCTO_PRECIO_URI = "http://localhost:8081/productos/%d/precio";

    // HttpClient es reutilizable y seguro para múltiples hilos
    private static final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static String getArticulo(int id) {
        return llamarServicioExterno(PRODUCTO_URI.formatted(id));
    }

    public static String getPrecio(int id) {
        return llamarServicioExterno(PRODUCTO_PRECIO_URI.formatted(id));
    }

    private static String llamarServicioExterno(String url) {
        log.info("Llamando a {}", url);
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(10))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Verificar el código de estado de la respuesta
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                log.error("Error en la solicitud: código de estado {}", response.statusCode());
                throw new RuntimeException("Error al llamar al servicio externo. Código de estado: " + response.statusCode());
            }

        } catch (Exception e) {
            log.error("Error al llamar al servicio externo", e);
            throw new RuntimeException(e);
        }
    }
}
