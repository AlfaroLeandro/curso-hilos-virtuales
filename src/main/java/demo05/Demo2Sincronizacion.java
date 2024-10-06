package demo05;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Demo2Sincronizacion {
    private static final Logger log = LoggerFactory.getLogger(Demo2Sincronizacion.class);
    private static final List<String> list = new ArrayList<>();

    public static void main(String[] args) {

        demo(Thread.ofVirtual());

        Utiles.sleep(Duration.ofSeconds(2));

        log.info("cantidad de a's: {}", list.size());
    }

    private static void demo(Thread.Builder builder){
        for (int i = 0; i < 50; i++) {
            builder.start(() -> {
                log.info("Tarea iniciada. {}", Thread.currentThread());
                for (int j = 0; j < 200; j++) {
                    inMemoryTask();
                }
                log.info("Tarea finalizada. {}", Thread.currentThread());
            });
        }
    }

    private synchronized static void inMemoryTask(){
        list.add("a");
    }

}
