package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

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
        var start = System.currentTimeMillis();
        runnable.run();
        var end = System.currentTimeMillis();
        return (end - start);
    }

}
