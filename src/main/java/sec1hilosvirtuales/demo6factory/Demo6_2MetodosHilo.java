package sec1hilosvirtuales.demo6factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;

public class Demo6_2MetodosHilo {

    private static final Logger log = LoggerFactory.getLogger(Demo6_2MetodosHilo.class);

    public static void main(String[] args) throws InterruptedException {
        join();
        Utiles.sleep(Duration.ofSeconds(1));
    }

    /*
        Chequear si es vitual
     */
    private static void isVirtual() {
        var hilo1 = Thread.ofVirtual().start(() -> Utiles.sleep(Duration.ofSeconds(2)));
        var hilo2 = Thread.ofPlatform().start(() -> Utiles.sleep(Duration.ofSeconds(2)));
        log.info("Es hilo1 virtual: {}", hilo1.isVirtual());
        log.info("Is hilo2 virtual: {}", hilo2.isVirtual());
        log.info("Es el hilo actual un hilo virtual: {}", Thread.currentThread().isVirtual());
    }


    /*
        Para manejar multiples operaciones de IO con hilos virtuales y esperar a que se completen
     */
    private static void join() throws InterruptedException {
        var hilo1 = Thread.ofVirtual().start(() -> {
            Utiles.sleep(Duration.ofSeconds(2));
            log.info("Llamada a servicio de productos");
        });
        var hilo2 = Thread.ofVirtual().start(() -> {
            Utiles.sleep(Duration.ofSeconds(2));
            log.info("Llamada a servicio de productos");
        });
        hilo1.join();
        hilo2.join();
    }

    /*
        Para interrumpir / parar la ejecucion de un hilo
        En algunos casos, Java lanza un Interrupted Exception, IO Exception, Socket Exception, etc.

        Tambien para chequear si el hilo actual esta interrumpido
        Thread.currentThread().isInterrupted() - retorna boolean

        while(!Thread.currentThread().isInterrupted()){
            Continuar trabajando
            ...
            ...
        }
     */
    private static void interrupt() {
        var hilo = Thread.ofVirtual().start(() -> {
            Utiles.sleep(Duration.ofSeconds(2));
            log.info("Llamada a servicio de productos");
        });
        log.info("esta el hilo interrumpido: {}", hilo.isInterrupted());
        hilo.interrupt();
        log.info("esta el hilo interrumpido: {}", hilo.isInterrupted());
    }

}
