package demo07;

import demo07.servicioexterno.ArticuloCliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

/*
    Obtener multiples productos en paralelo
 */
public class Demo3AccederResponseFuture {

    private static final Logger log = LoggerFactory.getLogger(Demo3AccederResponseFuture.class);

    public static void main(String[] args) throws Exception {

        try(var executor = Executors.newVirtualThreadPerTaskExecutor()){

            var product1 = executor.submit(() -> ArticuloCliente.getArticulo(1));
            var product2 = executor.submit(() -> ArticuloCliente.getArticulo(2));
            var product3 = executor.submit(() -> ArticuloCliente.getArticulo(3));

            log.info("producto-1: {}", product1.get());
            log.info("producto-2: {}", product2.get());
            log.info("producto-3: {}", product3.get());

        }

    }


}
