package demo07;

import demo07.servicioexterno.ArticuloCliente;
import demo07.concurrencia.ConcurrenciaLimitador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Demo6Semaforo {

    private static final Logger log = LoggerFactory.getLogger(Demo6Semaforo.class);

    public static void main(String[] args) throws Exception {
        var factory = Thread.ofVirtual().name("leandro", 1).factory();
        var limitador = new ConcurrenciaLimitador(Executors.newThreadPerTaskExecutor(factory), 3);
        ejecutar(limitador, 200);
    }

    private static void ejecutar(ConcurrenciaLimitador limitador, int cantidadTareas) throws Exception {
        try(limitador){
            IntStream.rangeClosed(1, cantidadTareas)
                    .mapToObj(i -> (Callable<String>) () -> imprimirArticulo(i))
                    .forEach(limitador::submit);
            log.info("subido");
        }
    }

    private static String imprimirArticulo(int id){
        var product = ArticuloCliente.getArticulo(id);
        log.info("{} => {}", id, product);
        return product;
    }
}
