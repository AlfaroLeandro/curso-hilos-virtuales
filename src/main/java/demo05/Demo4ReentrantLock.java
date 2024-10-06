package demo05;

import com.vinsguru.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
    Virtual Threads are indented for I/O tasks. This is a simple demo the use of ReentrantLock
 */
public class Demo4ReentrantLock {

    private static final Logger log = LoggerFactory.getLogger(Lec04ReentrantLock.class);
    private static final Lock lock = new ReentrantLock();
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
