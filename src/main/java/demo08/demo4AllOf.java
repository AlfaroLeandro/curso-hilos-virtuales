package demo08;

import demo08.agregador.AgregadorServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/*
    Demo usando All of
 */
public class demo4AllOf {

    private static final Logger log = LoggerFactory.getLogger(demo4AllOf.class);

    public static void main(String[] args) {

        // beans / singletons
        var executor = Executors.newVirtualThreadPerTaskExecutor();
        var aggregator = new AgregadorServicio(executor);

        // create futures
        var futures = IntStream.rangeClosed(52, 100)
                               .mapToObj(id -> CompletableFuture.supplyAsync(() -> aggregator.getArticulo(id), executor))
                               .toList();

        // wait for all the completable-futures to complete
        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();

        var list = futures.stream()
                          .map(CompletableFuture::join)
                          .toList();

        log.info("list: {}", list);
    }


}
