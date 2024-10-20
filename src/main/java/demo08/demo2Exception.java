package demo08;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;


public class demo2Exception {

    private static final Logger log = LoggerFactory.getLogger(demo2Exception.class);

    public String holaMundo3LlamadasAsyncHandle() {
        return Utiles.timer(() -> {
            CompletableFuture<String> hola = CompletableFuture.supplyAsync(CommonsHolaMundo::hola);
            CompletableFuture<String> mundo = CompletableFuture.supplyAsync(CommonsHolaMundo::mundo);
            CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
                Utiles.sleep(Duration.ofMillis(1000));
                return " Soy un completable Future!";
            });

            return hola
                    .handle((resultado, e) -> { // se invoca para ambos exito y fallo
                        log.info(("el resultado es : " + resultado));
                        if (e != null) {
                            log.info(("Exception : " + e.getMessage()));
                            return "";
                        }
                        return resultado;

                    })
                    .thenCombine(mundo, (h, w) -> h + w) // (primero, segundo)
                    .handle((resultado, e) -> { // se invoca para ambos exito y fallo
                        log.info(("el resultado es : " + resultado));
                        if (e != null) {
                            log.info(("manejador desdepues de mundo : " + e.getMessage()));
                            return "";
                        }
                        return resultado;
                    })
                    .thenCombine(cf, (anterior, actual) -> anterior + actual)
                    .thenApply(String::toUpperCase)
                    .join();
        });

    }

    public String holaMundo3LlamadasAsyncExceptionally() {
        return Utiles.timer(() -> {
            CompletableFuture<String> hola = CompletableFuture.supplyAsync(CommonsHolaMundo::hola);
            CompletableFuture<String> mundo = CompletableFuture.supplyAsync(CommonsHolaMundo::mundo);
            CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
                Utiles.sleep(Duration.ofMillis(1000));
                return " Soy un completable Future!";
            });

            return hola
                    .exceptionally((e) -> { // se invoca para ambos exito y fallo
                            log.info(("Exception : " + e.getMessage()));
                        return "";
                    })
                    .thenCombine(mundo, (h, w) -> h + w) // (primero, segundo)
                    .exceptionally((e) -> { // se invoca para ambos exito y fallo
                            log.info(("manejador desdepues de mundo : " + e.getMessage()));
                            return "";
                    })
                    .thenCombine(cf, (anterior, actual) -> anterior + actual)
                    .thenApply(String::toUpperCase)

                    .join();

        });

    }


    public String holaMundo3LlamadasAsyncWhenComplete() {
        return Utiles.timer(() -> {
            CompletableFuture<String> hola = CompletableFuture.supplyAsync(CommonsHolaMundo::hola);
            CompletableFuture<String> mundo = CompletableFuture.supplyAsync(CommonsHolaMundo::mundo);
            CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
                Utiles.sleep(Duration.ofMillis(1000));
                return " Soy un completable Future!";
            });

            return hola
                    .whenComplete((resultado, e) -> { // se invoca para ambos exito y fallo
                        log.info(("el resultado es : " + resultado));
                        if (e != null) {
                            log.info(("Exception : " + e.getMessage()));
                        }
                    })
                    .thenCombine(mundo, (h, w) -> h + w) // (primero, segundo)
                    .whenComplete((resultado, e) -> { // se invoca para ambos exito y fallo
                        log.info(("el resultado es : " + resultado));
                        if (e != null) {
                            log.info(("manejador desdepues de mundo : " + e.getMessage()));
                        }
                    })
                    .exceptionally((e) -> { // se invoca para ambos exito y fallo
                        log.info(("manejador desdepues de mundo : " + e.getMessage()));
                        return "";
                    })
                    .thenCombine(cf, (anterior, actual) -> anterior + actual)
                    .thenApply(String::toUpperCase)

                    .join();
        });
    }

}
