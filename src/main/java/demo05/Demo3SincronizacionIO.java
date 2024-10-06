package demo05;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Demo3SincronizacionIO {
    private static final Logger log = LoggerFactory.getLogger(Demo3SincronizacionIO.class);
    private static final List<String> list = new ArrayList<>();

    // Usa esto para ver los hilos pinned
    static {
        System.setProperty("jdk.tracePinnedThreads", "short");
    }

    public static void main(String[] args) {

        Runnable runnable = () -> log.info("#!#!#!#!# Mensaje de prueba #!#!#!#!");

// We will not see this issue wit Platform threads
//        demo(Thread.ofPlatform());
//        Thread.ofPlatform().start(runnable);

        demo(Thread.ofVirtual());
        Thread.ofVirtual().start(runnable);

        Utiles.sleep(Duration.ofSeconds(15));

    }

    private static void demo(Thread.Builder builder){
        for (int i = 0; i < 50; i++) {
            builder.start(() -> {
                log.info("Tarea iniciada. {}", Thread.currentThread());
                ioTask();
                log.info("Tarea finalizada. {}", Thread.currentThread());
            });
        }
    }

    private static synchronized void ioTask(){
        Utiles.sleep(Duration.ofSeconds(10));
    }

}
