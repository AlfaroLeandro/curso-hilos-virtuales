package sec2executors;

import sec2executors.servicioexterno.ArticuloCliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Demo7Planificado {

    private static final Logger log = LoggerFactory.getLogger(Demo7Planificado.class);

    public static void main(String[] args) {
        scheduled();
    }

    // tareas periodicas
    private static void scheduled(){
        var scheduler = Executors.newSingleThreadScheduledExecutor();
        var executor = Executors.newVirtualThreadPerTaskExecutor();
        try(scheduler; executor){
            scheduler.scheduleAtFixedRate(() -> {
                executor.submit(() -> imprimirArticulo(1));
            }, 0, 3, TimeUnit.SECONDS);

            Utiles.sleep(Duration.ofSeconds(12));
        }
    }

    private static void imprimirArticulo(int id){
        log.info("{} => {}", id, ArticuloCliente.getArticulo(id));
    }

}
