package demo02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;

public class StackDemo {
    private static final Logger log = LoggerFactory.getLogger(StackDemo.class);


    public static void main(String[] args) {
        demo(Thread.ofVirtual().name("virtual-", 1));

        Utiles.sleep(Duration.ofSeconds(2));

    }

    private static void demo(Thread.Builder builder){
        for (int i = 1; i <= 20 ; i++) {
            int j = i;
            builder.start(() -> ejecutar(j));
        }
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
        Utiles.sleep(Duration.ofMillis(300));
        try{
            metodo2(i);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private static void metodo2(int i){
        Utiles.sleep(Duration.ofMillis(100));
        metodo3(i);
    }

    private static void metodo3(int i){
        Utiles.sleep(Duration.ofMillis(500));
        if(i == 4){
            throw new IllegalArgumentException("No puedo ser 4");
        }
    }
}
