package demo07;

import demo07.servicioexterno.ArticuloCliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class demo5Concurrencia {

    private static final Logger log = LoggerFactory.getLogger(demo5Concurrencia.class);

    public static void main(String[] args) {
        // No se supone que se usen así los hilos virtuales
        var factory = Thread.ofVirtual().name("leandro", 1).factory();
        ejecutar(Executors.newFixedThreadPool(3), 20);
    }

    private static void ejecutar(ExecutorService executorService, int cantidadTareas){
        try(executorService){
            IntStream.rangeClosed(1, cantidadTareas)
                     .forEachOrdered(demo5Concurrencia::imprimirArticulo);
            log.info("subido");
        }
    }

    private static void imprimirArticulo(int id){
        log.info("{} => {}", id, ArticuloCliente.getArticulo(id));
    }
}
