package sec1hilosvirtuales;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;
import java.util.stream.IntStream;

public class Demo4Planificacion {
    private static final Logger log = LoggerFactory.getLogger(Demo4Planificacion.class);

    public static void main(String[] args) {
        System.setProperty("jdk.virtualThreadScheduler.parallelism", "1");
        System.setProperty("jdk.virtualThreadScheduler.maxPoolSize", "1");

        var builder = Thread.ofVirtual();
        IntStream.rangeClosed(1, 3)
                 .forEach(i -> builder.unstarted(() -> ejecutarTareaConYield(i)).start());

        Utiles.sleep(Duration.ofSeconds(2));
    }

    private static void ejecutarTareaConYield(int id){
        for (int i = 0; i < 5; i++) {
            log.info("id {} - Iteracion {} - Hilo {}", id, i, Thread.currentThread());

            // Usamos yield para ceder el control y permitir que otros hilos se ejecuten
            Thread.yield();

            // Simulación de trabajo
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.info("Hilo {} fue interrumpido", id);
                break;
            }
        }
        log.info("Hilo {} finalizado", id);
    }

}
