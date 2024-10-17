package demo04;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;
import java.util.stream.IntStream;

public class PlanificacionCooperativaDemo {
    private static final Logger log = LoggerFactory.getLogger(PlanificacionCooperativaDemo.class);

    public static void main(String[] args) {
        System.setProperty("jdk.virtualThreadScheduler.parallelism", "1");
        System.setProperty("jdk.virtualThreadScheduler.maxPoolSize", "1");

        var builder = Thread.ofVirtual();
        IntStream.rangeClosed(1, 3)
                 .forEach(i -> builder.unstarted(() -> ejecutar(i)).start());

        Utiles.sleep(Duration.ofSeconds(2));
    }

    private static void ejecutar(int numeroHilo){
        log.info("iniciando hilo {}", numeroHilo);

        IntStream.range(0, 10)
                .forEachOrdered(i -> {
                    log.info("hilo {} esta imprimiendo {}. Thread: {}", numeroHilo, i, Thread.currentThread());
                    Thread.yield();
                });
        log.info("hilo {} finalizado", numeroHilo);
    }

}
