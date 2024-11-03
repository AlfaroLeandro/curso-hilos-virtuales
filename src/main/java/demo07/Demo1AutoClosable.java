package demo07;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ExecutorService ahora extiende de AutoCloseable
 */
public class Demo1AutoClosable {

    private static final Logger log = LoggerFactory.getLogger(Demo1AutoClosable.class);

    public static void main(String[] args) {

    }

    public static void ejemploSinAutocloseable() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        try {
            // Enviamos una tarea al executor
            executor.submit(() -> {
                System.out.println("Tarea ejecutada en el executor sin AutoCloseable");
            });
        } finally {
            // Cerramos el executor manualmente
            if (!executor.isShutdown()) {
                executor.shutdown();
                System.out.println("El executor se ha cerrado manualmente.");
            }
        }
    }

    public static void ejemploAutocloseable() {
        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
            // Enviamos una tarea al executor
            executor.submit(() -> {
                System.out.println("Tarea ejecutada en el executor usando AutoCloseable");
            });
        }
        // El executor se cerrará automáticamente aquí, al salir del bloque try
        System.out.println("El executor se ha cerrado automáticamente.");
    }

}
