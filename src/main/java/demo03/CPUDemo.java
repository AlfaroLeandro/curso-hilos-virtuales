package demo03;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.util.concurrent.CountDownLatch;

public class CPUDemo {
    private static final Logger log = LoggerFactory.getLogger(CPUDemo.class);
    private static final int CANTIDAD_TAREAS = 3 * Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        log.info("Cantidad de tareas: {}", CANTIDAD_TAREAS);
        for (int i = 0; i < 3; i++) {
            var tiempo = Utiles.timer(() -> demo(Thread.ofVirtual()));
            log.info("tiempo total hilos virtuales {} ms", tiempo);
            tiempo = Utiles.timer(() -> demo(Thread.ofPlatform()));
            log.info("tiempo total hilos de plataforma {} ms", tiempo);
        }

    }

    private static void demo(Thread.Builder builder){
        var latch = new CountDownLatch(CANTIDAD_TAREAS);
        for (int i = 1; i <= CANTIDAD_TAREAS; i++) {
            builder.start(() ->{
                operacionCPU(45);
                latch.countDown();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static void operacionCPU(int i){
        log.info("Empezando la tarea de CPU. Info del hilo: {}", Thread.currentThread());
        var tiempo = Utiles.timer(() -> fibonacci(i));
        log.info("Finalizando la tarea de CPU. tiempo total: {} ms", tiempo);
    }


    // Algoritmo con O(2^n) Intensa en CPU
    public static long fibonacci(long n) {
        if (n < 2)
            return n;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

}
