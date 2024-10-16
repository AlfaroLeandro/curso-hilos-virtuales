package demo02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;
import java.util.stream.IntStream;

public class StackDemo {
    private static final Logger log = LoggerFactory.getLogger(StackDemo.class);


    public static void main(String[] args) {
        demo(Thread.ofVirtual().name("virtual-", 1));

        Utiles.sleep(Duration.ofSeconds(2));

    }

    private static void demo(Thread.Builder builder){
        IntStream.rangeClosed(1, 20)
                .forEach(i -> builder.start(() -> ejecutar(i)));
    }

    public static void ejecutar(int i){
        log.info("Iniciando tarea {}", i);
        try{
            metodo1(i);
        }catch (Exception e){
            log.error("error {}", i, e);
        }
        log.info("Finalizando Tarea {}", i);
    }

    private static void metodo1(int i){
        Utiles.sleep(Duration.ofMillis(150));
        try{
            metodo2(i);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private static void metodo2(int i){
        Utiles.sleep(Duration.ofMillis(200));
        metodo3(i);
    }

    private static void metodo3(int i){
        Utiles.sleep(Duration.ofMillis(450));
        if(i == 10){
            throw new IllegalArgumentException("No puedo ser 10");
        }
    }
}
