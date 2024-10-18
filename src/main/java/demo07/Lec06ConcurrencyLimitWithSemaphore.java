package demo07;

import demo07.externalservice.Client;
import demo07.concurrencylimit.ConcurrenciaLimitador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

public class Lec06ConcurrencyLimitWithSemaphore {

    private static final Logger log = LoggerFactory.getLogger(Lec06ConcurrencyLimitWithSemaphore.class);

    public static void main(String[] args) throws Exception {
        var factory = Thread.ofVirtual().name("vins", 1).factory();
        var limiter = new ConcurrenciaLimitador(Executors.newThreadPerTaskExecutor(factory), 3);
        execute(limiter, 200);
    }

    private static void execute(ConcurrenciaLimitador concurrencyLimiter, int taskCount) throws Exception {
        try(concurrencyLimiter){
            for (int i = 1; i <= taskCount; i++) {
                int j = i;
                concurrencyLimiter.submit(() -> printProductInfo(j));
            }
            log.info("submitted");
        }
    }

    // 3rd party service
    // contract: 3 concurrent calls are allowed
    private static String printProductInfo(int id){
        var product = Client.getProducto(id);
        log.info("{} => {}", id, product);
        return product;
    }
}
