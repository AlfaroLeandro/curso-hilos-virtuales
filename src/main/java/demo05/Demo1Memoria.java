package demo05;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * UNIR DEMOS 1, 2, 4
 * UNIR DEMOS 3, 5
 */
public class Demo1Memoria {
    private static final Logger log = LoggerFactory.getLogger(Demo1Memoria.class);
    private static final List<String> list = new ArrayList<>();

    private static final Lock lock = new ReentrantLock(); //va despues


    public static void main(String[] args) {

        demo(Thread.ofVirtual(), Demo1Memoria::tareaEnMemoria);
        demo(Thread.ofVirtual(), Demo1Memoria::tareaEnMemoriaSynchronized);
        demo(Thread.ofVirtual(), Demo1Memoria::tareaEnMemoriaLock);

        Utiles.sleep(Duration.ofSeconds(2));

        log.info("cantidad de a's: {}", list.size());
    }

    private static void demo(Thread.Builder builder, Runnable tarea){
        IntStream.range(0, 50)
                .forEach(_ -> {
                    builder.start(() -> {
                        log.info("Tarea iniciada. {}", Thread.currentThread());
                        for (int i = 0; i < 200; i++)
                            tarea.run();
                        log.info("Tarea finalizada. {}", Thread.currentThread());
                    });
                });
    }

    private static void tareaEnMemoria(){
        list.add("a");
    }

    private synchronized static void tareaEnMemoriaSynchronized(){
        list.add("a");
    }

    private static void tareaEnMemoriaLock(){
        try{
            lock.lock();
            list.add("a");
        }catch (Exception e){
            log.error("error", e);
        }finally {
            lock.unlock();
        }
    }


}
