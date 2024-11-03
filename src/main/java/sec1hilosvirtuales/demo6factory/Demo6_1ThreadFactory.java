package sec1hilosvirtuales.demo6factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;
import java.util.concurrent.ThreadFactory;
import java.util.stream.IntStream;

public class Demo6_1ThreadFactory {

    private static final Logger log = LoggerFactory.getLogger(Demo6_1ThreadFactory.class);

    public static void main(String[] args) {

        demo(Thread.ofVirtual().name("leandro", 1).factory());

        Utiles.sleep(Duration.ofSeconds(3));

    }

    /*
        Crear hilos con tareas
        Cada hilo crea hilos con 1 subtarea
        En la vida real usamos ExecutorService, etc
        Los hilos vituales son baratos de crear
     */

    private static void demo(ThreadFactory factory){
        IntStream.range(0, 30)
                .forEach(i ->{
                    var t = factory.newThread(() -> {
                        log.info("Tarea iniciada. {}", Thread.currentThread());
                        var ct = factory.newThread(() -> {
                            log.info("SubTarea iniciada. {}", Thread.currentThread());
                            Utiles.sleep(Duration.ofSeconds(2));
                            log.info("SubTarea finalizada. {}", Thread.currentThread());
                        });
                        ct.start();
                        log.info("Tarea finalizada. {}", Thread.currentThread());
                    });
                    t.start();
                });
    }
}
