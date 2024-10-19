package demo07.servicioexterno;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class ArticuloCliente {

    private static final Logger log = LoggerFactory.getLogger(ArticuloCliente.class);
    private static final String PRODUCTO_URI = "http://localhost:8081/productos/product/%d";
    private static final String PRODUCTO_PRECIO_URI = "http://localhost:8081/productos/%d/precio";

    public static String getArticulo(int id){
        return llamarServicioExterno(PRODUCTO_URI.formatted(id));
    }

    public static String getPrecio(int id){
        return llamarServicioExterno(PRODUCTO_PRECIO_URI.formatted(id));
    }

    private static String llamarServicioExterno(String url){
        log.info("Llamando {}", url);
        try(var stream = URI.create(url).toURL().openStream()){
            return new String(stream.readAllBytes());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
