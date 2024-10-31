package demo03;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class CPUDemo {
    private static final Logger log = LoggerFactory.getLogger(CPUDemo.class);
    private static final int CANTIDAD_TAREAS = 3 * Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        log.info("Cantidad de tareas: {}", CANTIDAD_TAREAS);
        IntStream.range(0, 3)
                .forEach(_ -> {
                    var tiempo = Utiles.timer(() -> demo(Thread.ofVirtual()));
                    log.info("tiempo total hilos virtuales {} ms", tiempo);
                    tiempo = Utiles.timer(() -> demo(Thread.ofPlatform()));
                    log.info("tiempo total hilos de plataforma {} ms", tiempo);
                });
    }

    private static void demo(Thread.Builder builder){
        var latch = new CountDownLatch(CANTIDAD_TAREAS);
        IntStream.rangeClosed(1, CANTIDAD_TAREAS)
                .forEach(_ ->
                        builder.start(() ->{
                            Utiles.timer(() -> fibonacci(45));
                            latch.countDown();
                        }));
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    // Algoritmo con O(2^n) Intensa en CPU
    public static long fibonacci(long n) {
        if (n < 2)
            return n;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

}
