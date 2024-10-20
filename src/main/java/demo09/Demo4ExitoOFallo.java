package demo09;

import util.Utiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.ThreadLocalRandom;

/*
    Demo de concurrencia estructurada
 */
public class Demo4ExitoOFallo {

    private static final Logger log = LoggerFactory.getLogger(Demo4ExitoOFallo.class);

    public static void main(String[] args) {

        try(var taskScope = new StructuredTaskScope<>()){
            var subtarea1 = taskScope.fork(Demo4ExitoOFallo::servicioCalcularPrecioArticulo);
            var subtarea2 = taskScope.fork(Demo4ExitoOFallo::tareaError);

            taskScope.join();

            log.info("subtarea1 estado: {}", subtarea1.state());
            log.info("subtarea2 estado: {}", subtarea2.state());

            log.info("subtarea1 resultado: {}", subtarea1.get());
            log.info("subtarea2 resultado: {}", subtarea2.get());

        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    private static String servicioCalcularPrecioArticulo(){
        var random = ThreadLocalRandom.current().nextInt(100, 1000);
        log.info("resultado: {}", random);
        Utiles.sleep("calculando valor", Duration.ofSeconds(1));
        return "valor: $" + random;
    }

    private static String servicioCalcularPrecioArticulo2(){
        var random = ThreadLocalRandom.current().nextInt(100, 1000);
        log.info("resultado: {}", random);
        Utiles.sleep("calculando valor", Duration.ofSeconds(1));
        return "valor: $" + random;
    }

    private static String tareaError(){
        throw new RuntimeException("error");
    }

}
