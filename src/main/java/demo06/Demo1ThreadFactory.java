package demo06;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;
import java.util.concurrent.ThreadFactory;

public class Demo1ThreadFactory {

    private static final Logger log = LoggerFactory.getLogger(Demo1ThreadFactory.class);

    public static void main(String[] args) {

        demo(Thread.ofVirtual().name("leandro", 1).factory());

        Utiles.sleep(Duration.ofSeconds(3));

    }

    /*
        Create few threads
        Each thread creates 1 child thread
        It is a simple demo. In the real life, lets use ExecutorService etc
        Virtual threads are cheap to create.
     */

    private static void demo(ThreadFactory factory){
        for (int i = 0; i < 30; i++) {
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
        }
    }
}
