package demo08;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;


public class demo2Exception {

    private static final Logger log = LoggerFactory.getLogger(demo1HolaMundo.class);

    public String helloWorld_3_async_calls_handle() {
        return Utiles.timer(() -> {
            CompletableFuture<String> hello = CompletableFuture.supplyAsync(CommonsHolaMundo::hola);
            CompletableFuture<String> world = CompletableFuture.supplyAsync(CommonsHolaMundo::mundo);
            CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
                Utiles.sleep(Duration.ofMillis(1000));
                return " HI CompletableFuture!";
            });

            return hello
                    .handle((result, e) -> { // this gets invoked for both success and failure
                        log.info(("result is : " + result));
                        if (e != null) {
                            log.info(("Exception is : " + e.getMessage()));
                            return "";
                        }
                        return result;

                    })
                    .thenCombine(world, (h, w) -> h + w) // (first,second)
                    .handle((result, e) -> { // this gets invoked for both success and failure
                        log.info(("result is : " + result));
                        if (e != null) {
                            log.info(("Exception Handle after world : " + e.getMessage()));
                            return "";
                        }
                        return result;
                    })
                    .thenCombine(hiCompletableFuture, (previous, current) -> previous + current)
                    .thenApply(String::toUpperCase)
                    .join();
        });

    }

    public String helloWorld_3_async_calls_exceptionally() {
        return Utiles.timer(() -> {
            CompletableFuture<String> hello = CompletableFuture.supplyAsync(CommonsHolaMundo::hola);
            CompletableFuture<String> world = CompletableFuture.supplyAsync(CommonsHolaMundo::mundo);
            CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
                Utiles.sleep(Duration.ofMillis(1000));
                return " HI CompletableFuture!";
            });

            return hello
                    .exceptionally((e) -> { // this gets invoked for both success and failure
                            log.info(("Exception is : " + e.getMessage()));
                        return "";
                    })
                    .thenCombine(world, (h, w) -> h + w) // (first,second)
                    .exceptionally((e) -> { // this gets invoked for both success and failure
                            log.info(("Exception Handle after world : " + e.getMessage()));
                            return "";
                    })
                    .thenCombine(hiCompletableFuture, (previous, current) -> previous + current)
                    .thenApply(String::toUpperCase)

                    .join();

        });

    }


    public String helloWorld_3_async_whenComplete() {
        return Utiles.timer(() -> {
            CompletableFuture<String> hello = CompletableFuture.supplyAsync(CommonsHolaMundo::hola);
            CompletableFuture<String> world = CompletableFuture.supplyAsync(CommonsHolaMundo::mundo);
            CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
                Utiles.sleep(Duration.ofMillis(1000));
                return " HI CompletableFuture!";
            });

            return hello
                    .whenComplete((result, e) -> { // this gets invoked for both success and failure
                        log.info(("result is : " + result));
                        if (e != null) {
                            log.info(("Exception is : " + e.getMessage()));
                        }
                    })
                    .thenCombine(world, (h, w) -> h + w) // (first,second)
                    .whenComplete((result, e) -> { // this gets invoked for both success and failure
                        log.info(("result is : " + result));
                        if (e != null) {
                            log.info(("Exception Handle after world : " + e.getMessage()));
                        }
                    })
                    .exceptionally((e) -> { // this gets invoked for both success and failure
                        log.info(("Exception Handle after world : " + e.getMessage()));
                        return "";
                    })
                    .thenCombine(hiCompletableFuture, (previous, current) -> previous + current)
                    .thenApply(String::toUpperCase)

                    .join();
        });
    }

}
