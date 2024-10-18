package demo07;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/*
    varios tipos de executors
 */
public class Demo2ExecutorsTipos {

    private static final Logger log = LoggerFactory.getLogger(Demo2ExecutorsTipos.class);

    public static void main(String[] args) {

    }

    // single thread executor - ejecutar tareas secuencialmente
    private static void single(){
        ejecutar(Executors.newSingleThreadExecutor(), 3);
    }

    // fixed thread pool
    private static void fixed(){
        ejecutar(Executors.newFixedThreadPool(5), 20);
    }

    // elastic thread pool
    private static void cached(){
        ejecutar(Executors.newCachedThreadPool(), 200);
    }

    // ExecutorService que crea hilos virtuales por tarea
    private static void virtual(){
        ejecutar(Executors.newVirtualThreadPerTaskExecutor(), 10_000);
    }

    // planificar tareas periodicamente
    private static void scheduled(){
        try(var executorService = Executors.newSingleThreadScheduledExecutor()){
            executorService.scheduleAtFixedRate(() -> {
                log.info("Ejecutando tarea");
            }, 0, 1, TimeUnit.SECONDS);

            Utiles.sleep(Duration.ofSeconds(5));
        }
    }

    private static void ejecutar(ExecutorService executorService, int taskCount){
        try(executorService){
            IntStream.range(0, taskCount).forEach(Demo2ExecutorsTipos::tareaES);
            log.info("subido");
        }
    }

    private static void tareaES(int i){
        log.info("Tarea iniciada: {}. Thread Info {}", i, Thread.currentThread());
        Utiles.sleep(Duration.ofSeconds(5));
        log.info("Tarea finalizada: {}. Thread Info {}", i, Thread.currentThread());
    }

}
