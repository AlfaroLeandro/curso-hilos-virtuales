package demo07.externalservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class Client {

    private static final Logger log = LoggerFactory.getLogger(Client.class);
    private static final String PRODUCT_REQUEST_FORMAT = "http://localhost:8081/productos/product/%d";
    private static final String RATING_REQUEST_FORMAT = "http://localhost:8081/productos/rating/%d";

    public static String getProducto(int id){
        return llamarServicioExterno(PRODUCT_REQUEST_FORMAT.formatted(id));
    }

    public static Integer getRating(int id){
        return Integer.parseInt(
                llamarServicioExterno(RATING_REQUEST_FORMAT.formatted(id))
        );
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