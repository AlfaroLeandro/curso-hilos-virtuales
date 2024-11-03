package demo08.servicio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class HolaMundoServicio {
    private static final Logger log = LoggerFactory.getLogger(HolaMundoServicio.class);

    public String holaMundo() {
        Utiles.sleep(Duration.ofMillis(1000));
        log.info("dentro de hola mundo");
        return "hola mundo";
    }

    public String hola() {
        Utiles.sleep(Duration.ofMillis(1000));
        log.info("dentro de hola");
        return "hola";
    }

    public String mundo() {
        Utiles.sleep(Duration.ofMillis(1000));
        log.info("dentro de mundo");
        return " mundo!";
    }

    public CompletableFuture<String> mundoFuture(String input) {
        return CompletableFuture.supplyAsync(()->{
            Utiles.sleep(Duration.ofMillis(1000));
            return input+" mundo!";
        });
    }

}
