package demo09;

import util.Utiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.ThreadLocalRandom;

/*
    Demo de concurrencia estructurada cuando nosotros queremos cancelar todas las subtareas cuando la primera termina exitosamente
 */
public class Demo6PrimerExito {

    private static final Logger log = LoggerFactory.getLogger(Demo6PrimerExito.class);

    public static void main(String[] args) {

        try(var taskScope = new StructuredTaskScope.ShutdownOnSuccess<>()){
            var subtask1 = taskScope.fork(CommonsDemo09::tareaError);
            var subtask2 = taskScope.fork(CommonsDemo09::servicioCalcularPrecioArticulo);

            taskScope.join();

            log.info("subtask1 state: {}", subtask1.state());
            log.info("subtask2 state: {}", subtask2.state());

            log.info("subtask result: {}", taskScope.result(ex -> new RuntimeException("all failed")));


        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

}
