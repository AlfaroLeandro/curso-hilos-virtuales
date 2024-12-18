package sec4completablefuture;

import sec4completablefuture.agregador.AgregadorServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

/*
    Aggregate and error handling
 */
public class demo3Agregador {

    private static final Logger log = LoggerFactory.getLogger(demo3Agregador.class);

    public static void main(String[] args) throws Exception {
        // beans / singletons
        var executor = Executors.newVirtualThreadPerTaskExecutor();
        var agregador = new AgregadorServicio(executor);

        log.info("articulo={}", agregador.getArticulo(51));
    }

}
