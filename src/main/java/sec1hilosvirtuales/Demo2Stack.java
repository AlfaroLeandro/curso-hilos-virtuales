package sec1hilosvirtuales;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Demo2Stack {
    private static final Logger log = LoggerFactory.getLogger(Demo2Stack.class);


    public static void main(String[] args) {
        demo(Thread.ofVirtual().name("virtual-", 1));

        Utiles.sleep(Duration.ofSeconds(2));

    }

    private static void demo(Thread.Builder builder){
        IntStream.rangeClosed(1, 20)
                .forEach(i -> metodoRecursivo());
    }

    public static void metodoRecursivo() {
        double probabilidad = ThreadLocalRandom.current().nextDouble(0, 1);
        if(probabilidad<=0.9)
            metodoRecursivo2();
        if(probabilidad<=0.95)
            metodoRecursivo3();
    }

    public static void metodoRecursivo2() {
        double probabilidad = ThreadLocalRandom.current().nextDouble(0, 1);
        if(probabilidad<=0.9)
            metodoRecursivo3();
        if(probabilidad<=0.95)
            metodoRecursivo2();

        metodoRecursivo();
    }

    public static void metodoRecursivo3() {
        double probabilidad = ThreadLocalRandom.current().nextDouble(0, 1);
        if(probabilidad<=0.9)
            metodoRecursivo();
        if(probabilidad<=0.95)
            return;
        throw new RuntimeException("No podia llegar aca");
    }
}
