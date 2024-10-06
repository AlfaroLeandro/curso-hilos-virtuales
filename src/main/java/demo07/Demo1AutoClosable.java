package demo07;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;
import java.util.concurrent.Executors;

/*
    ExecutorService ahora extiende de AutoCloseable
*/
public class Demo1AutoClosable {

    private static final Logger log = LoggerFactory.getLogger(Demo1AutoClosable.class);

    public static void main(String[] args) {

    }

    // w/o autocloseable - habia que cerrar el executor service
    private static void withoutAutoCloseable(){
        var executorService = Executors.newSingleThreadExecutor();
        executorService.submit(Demo1AutoClosable::tarea);
        log.info("submitted");
        executorService.shutdown();
    }

    private static void withAutoCloseable(){
        try(var executorService = Executors.newSingleThreadExecutor()){
            executorService.submit(Demo1AutoClosable::tarea);
            executorService.submit(Demo1AutoClosable::tarea);
            executorService.submit(Demo1AutoClosable::tarea);
            executorService.submit(Demo1AutoClosable::tarea);
            log.info("submitted");
        }
    }

    private static void tarea(){
        Utiles.sleep(Duration.ofSeconds(1));
        log.info("Tarea ejecutada");
    }

}
