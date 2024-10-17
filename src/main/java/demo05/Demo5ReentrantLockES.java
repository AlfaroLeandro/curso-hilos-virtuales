package demo05;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo5ReentrantLockES {
    private static final Logger log = LoggerFactory.getLogger(Demo5ReentrantLockES.class);
    private static final Lock lock = new ReentrantLock();

    static {
        System.setProperty("jdk.tracePinnedThreads", "short");
    }

    public static void main(String[] args) {

        Runnable runnable = () -> log.info("#!#!#!#!# Mensaje de prueba #!#!#!#!#!");

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

    private static void ioTask(){
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
