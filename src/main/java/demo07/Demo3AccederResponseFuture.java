package demo07;

import demo07.agregador.ArticuloDTO;
import demo07.servicioexterno.ArticuloCliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

/*
    Obtener multiples productos en paralelo
 */
public class Demo3AccederResponseFuture {

    private static final Logger log = LoggerFactory.getLogger(Demo3AccederResponseFuture.class);

    public static void main(String[] args) throws Exception {

        try(var executor = Executors.newVirtualThreadPerTaskExecutor()){
            for (int i = 1; i <= 5; i++) {
                final int j = i;
                Future<String> submit = executor.submit(() -> ArticuloCliente.getArticulo(j));
                log.info("articulo-{}: {}", j, submit.get());
            }
        }
    }

}
