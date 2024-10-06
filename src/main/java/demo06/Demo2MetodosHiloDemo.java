package demo06;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;

public class Demo2MetodosHiloDemo {

    private static final Logger log = LoggerFactory.getLogger(Demo2MetodosHiloDemo.class);

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
        To offload multiple time-consuming I/O calls to Virtual threads and wait for them to complete
        Note: We can do better in the actual application which we will develop later.
        It is a simple thread.join() demo
     */
    private static void join() throws InterruptedException {
        var t1 = Thread.ofVirtual().start(() -> {
            CommonUtils.sleep(Duration.ofSeconds(2));
            log.info("called product service");
        });
        var t2 = Thread.ofVirtual().start(() -> {
            CommonUtils.sleep(Duration.ofSeconds(2));
            log.info("called pricing service");
        });
        t1.join();
        t2.join();
    }

    /*
        To interrupt / stop the thread execution
        in some cases, java will throw interrupted exception, IO exception, socket exception etc

        We can also check if the current thread is interrupted
        Thread.currentThread().isInterrupted() - returns a boolean

        while(!Thread.currentThread().isInterrupted()){
            continue the work
            ...
            ...
        }
     */
    private static void interrupt() {
        var t1 = Thread.ofVirtual().start(() -> {
            CommonUtils.sleep(Duration.ofSeconds(2));
            log.info("called product service");
        });
        log.info("is t1 interrupted: {}", t1.isInterrupted());
        t1.interrupt();
        log.info("is t1 interrupted: {}", t1.isInterrupted());
    }

}
