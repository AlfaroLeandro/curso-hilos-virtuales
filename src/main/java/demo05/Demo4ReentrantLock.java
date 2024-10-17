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
 * Demo de ReentrantLock
 */
public class Demo4ReentrantLock {

    private static final Logger log = LoggerFactory.getLogger(Demo4ReentrantLock.class);
    private static final Lock lock = new ReentrantLock();
    private static final List<String> list = new ArrayList<>();

    public static void main(String[] args) {

        demo(Thread.ofVirtual());

        Utiles.sleep(Duration.ofSeconds(2));

        log.info("cantidad de a's: {}", list.size());
    }

    private static void demo(Thread.Builder builder){
        IntStream.range(0, 50)
                 .forEach(_ -> {
                    builder.start(() -> {
                        log.info("Tarea iniciada. {}", Thread.currentThread());
                        IntStream.range(0, 200).forEach(_ -> tareaEnMemoria());
                        log.info("Tarea finalizada. {}", Thread.currentThread());
                    });
                });
    }

    private synchronized static void tareaEnMemoria(){
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
