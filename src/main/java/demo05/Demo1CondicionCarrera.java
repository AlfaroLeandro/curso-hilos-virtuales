package demo05;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Demo1CondicionCarrera {
    private static final Logger log = LoggerFactory.getLogger(Demo1CondicionCarrera.class);
    private static final List<String> list = new ArrayList<>();

    public static void main(String[] args) {

        demo(Thread.ofVirtual());

        Utiles.sleep(Duration.ofSeconds(2));

        log.info("cantidad de a's: {}", list.size());
    }

    private static void demo(Thread.Builder builder){
        IntStream.range(0, 50)
                .forEachOrdered(_ -> {
                    builder.start(() -> {
                        log.info("Tarea iniciada. {}", Thread.currentThread());
                        IntStream.range(0, 200)
                                 .forEach(_ -> tareaEnMemoria());
                        log.info("Tarea finalizada. {}", Thread.currentThread());
                    });
                });
    }

    private static void tareaEnMemoria(){
        list.add("a");
    }

}
