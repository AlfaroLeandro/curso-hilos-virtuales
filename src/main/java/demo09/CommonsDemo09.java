package demo09;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

public abstract class CommonsDemo09 {
    private static final Logger log = LoggerFactory.getLogger(CommonsDemo09.class);

    public static String servicioCalcularPrecioArticulo(){
        var random = ThreadLocalRandom.current().nextInt(100, 1000);
        log.info("resultado: {}", random);
        Utiles.sleep("calculando valor", Duration.ofSeconds(1));
        return "valor: $" + random;
    }

    public static String servicioCalcularPrecioArticulo2(){
        var random = ThreadLocalRandom.current().nextInt(100, 1000);
        log.info("resultado: {}", random);
        Utiles.sleep("calculando valor", Duration.ofSeconds(1));
        return "valor: $" + random;
    }

    public static String tareaError(){
        throw new RuntimeException("error");
    }

}
