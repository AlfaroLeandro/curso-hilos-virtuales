package demo08;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class CommonsHolaMundo {
    private static final Logger log = LoggerFactory.getLogger(CommonsHolaMundo.class);

    public static String holaMundo() {
        Utiles.sleep(Duration.ofMillis(1000));
        log.info("dentro de hola mundo");
        return "hola mundo";
    }

    public static String hola() {
        Utiles.sleep(Duration.ofMillis(1000));
        log.info("dentro de hola");
        return "hola";
    }

    public static String mundo() {
        Utiles.sleep(Duration.ofMillis(1000));
        log.info("dentro de mundo");
        return " mundo!";
    }

    public static CompletableFuture<String> mundoFuture(String input) {
        return CompletableFuture.supplyAsync(()->{
            Utiles.sleep(Duration.ofMillis(1000));
            return input+" mundo!";
        });
    }
}
