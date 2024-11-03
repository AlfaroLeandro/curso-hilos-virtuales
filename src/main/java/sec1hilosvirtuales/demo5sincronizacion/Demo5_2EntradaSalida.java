package sec1hilosvirtuales.demo5sincronizacion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Demo5_2EntradaSalida {
    private static final Logger log = LoggerFactory.getLogger(Demo5_2EntradaSalida.class);
    private static final List<String> list = new ArrayList<>();

    private static final Lock lock = new ReentrantLock();


    public static void main(String[] args) {
        System.setProperty("jdk.tracePinnedThreads", "short"); // Usa esto para ver los hilos pinned

        Runnable runnable = () -> log.info("#!#!#!#!# Mensaje de prueba #!#!#!#!");

// We will not see this issue wit Platform threads
//        demo(Thread.ofPlatform());
//        Thread.ofPlatform().start(runnable);

        demo(Thread.ofVirtual(), Demo5_2EntradaSalida::tareaES);
        demo(Thread.ofVirtual(), Demo5_2EntradaSalida::tareaESLock);

        Thread.ofVirtual().start(runnable);

        Utiles.sleep(Duration.ofSeconds(15));

    }

    private static void demo(Thread.Builder builder, Runnable runnable){
        IntStream.range(0, 50)
                .forEach(_ -> {
                    log.info("Tarea iniciada. {}", Thread.currentThread());
                    runnable.run();
                    log.info("Tarea finalizada. {}", Thread.currentThread());
                });
    }

    private static synchronized void tareaES(){
        Utiles.sleep(Duration.ofSeconds(10));
    }

    private static void tareaESLock(){
        try{
            lock.lock();
            Utiles.sleep(Duration.ofSeconds(10));
        }catch (Exception e){
            log.error("error", e);
        }finally {
            lock.unlock();
        }
    }

}
