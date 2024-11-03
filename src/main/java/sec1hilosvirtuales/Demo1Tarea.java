package sec1hilosvirtuales;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class Demo1Tarea {
    
    public static final int CANTIDAD_HILOS_PLATAFORMA = 10_000;
    public static final int CANTIDAD_HILOS_VIRTUALES = 10_000;

    private static final Logger log = LoggerFactory.getLogger(Demo1Tarea.class);
    
    public static void main(String[] args) {
        demo1Plataforma();
    }

    /*
    To create a simple java platform thread
 */
    private static void demo1Plataforma() {
        IntStream.range(0, CANTIDAD_HILOS_PLATAFORMA)
                .mapToObj(i -> new Thread(() -> operacionEntradaSalida(i)))
                .forEach(Thread::start);
    }

    /**
     * Usando Thread.Builder
     */
    private static void demo2Plataforma(){
        var builder = Thread.ofPlatform().name("plataforma", 1);
        IntStream.range(0, CANTIDAD_HILOS_PLATAFORMA)
                .mapToObj(i -> builder.unstarted(() -> operacionEntradaSalida(i)))
                .forEach(Thread::start);
    }


    private static void demo3Demonio() throws InterruptedException {
        var builder = Thread.ofPlatform().daemon().name("demonio", 1);
        demoGenerico(CANTIDAD_HILOS_PLATAFORMA, builder);
    }

    /**
     * Hilos virtuales con Thread builder
     * Son de tipo demonio por default
     * No tienen nombre por default
     */
    private static void demo4Vritual() throws InterruptedException {
        var builder = Thread.ofVirtual().name("virtual-", 1);
        demoGenerico(CANTIDAD_HILOS_VIRTUALES, builder);
    }

    /**
     * Creando hilos demonio
     */
    private static void demoGenerico(int cantidadHilos, Thread.Builder builder) throws InterruptedException {
        var latch = new CountDownLatch(cantidadHilos);
        IntStream.range(0, cantidadHilos)
                .mapToObj(i ->
                    builder.unstarted(() -> {
                        operacionEntradaSalida(i);
                        latch.countDown();
                    }))
                .forEach(Thread::start);
        latch.await();
    }

    public static void operacionEntradaSalida(int i){
        log.info("empezando tarea de E/S {}. Thread Info: {}", i, Thread.currentThread());
        Utiles.sleep(Duration.ofSeconds(10));
        log.info("finalizando tarea de E/S {}. Thread Info: {}", i, Thread.currentThread());
    }
    
}
