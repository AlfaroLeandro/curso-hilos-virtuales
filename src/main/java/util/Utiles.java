package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public abstract class Utiles {

    private static final Logger log = LoggerFactory.getLogger(Utiles.class);

    public static void sleep(String nombreTarea, Duration duracion){
        try {
            Thread.sleep(duracion);
        } catch (InterruptedException e) {
            log.info("{} is cancelled", nombreTarea);
        }
    }

    public static void sleep(Duration duracion){
        try {
            Thread.sleep(duracion);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static long timer(Runnable runnable){
        var inicio = System.currentTimeMillis();
        runnable.run();
        var fin = System.currentTimeMillis();
        return (fin - inicio);
    }

    public static <T> T timerT(Supplier<T> supplierT){
        var inicio = System.currentTimeMillis();
        var future = supplierT.get();
        var fin = System.currentTimeMillis();
        log.info("Tiempo tomado: {}", fin - inicio);
        return future;
    }

}
