package demo08;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.stream.Collectors.joining;

public class demo1HolaMundo {

    private static final Logger log = LoggerFactory.getLogger(demo1HolaMundo.class);

    public static CompletableFuture<String> demo() {
        return CompletableFuture.supplyAsync(CommonsHolaMundo::holaMundo)//  common fork-join pool
                                .thenApply(String::toUpperCase);
    }

    public CompletableFuture<String> demoThenApply() {
        return CompletableFuture.supplyAsync(CommonsHolaMundo::holaMundo)//  common fork-join pool
                                .thenApply(String::toUpperCase)
                                .thenApply((s) -> s.length() + " - " + s);
    }

    public String demoLlamadasAsync() {
        return Utiles.timer(() -> {
                CompletableFuture<String> hello = CompletableFuture.supplyAsync(CommonsHolaMundo::hola);
                CompletableFuture<String> world = CompletableFuture.supplyAsync(CommonsHolaMundo::mundo);
    
                return hello
                    .thenCombine(world, (h, w) -> h + w) // (primero,segundo)
                    .thenApply(String::toUpperCase)
                    .join();
        });
    }


    public String demo3LlamadasAsync() {
        return Utiles.timer(() -> {
            CompletableFuture<String> hello = CompletableFuture.supplyAsync(CommonsHolaMundo::hola);
            CompletableFuture<String> world = CompletableFuture.supplyAsync(CommonsHolaMundo::mundo);
            CompletableFuture<String> soyUnCompletableFuture = CompletableFuture.supplyAsync(() -> {
                Utiles.sleep(Duration.ofMillis(1000));
                return " Soy un Completable future!";
            });

            return hello
                    .thenCombine(world, (h, w) -> h + w) // (primero, segundo)
                    .thenCombine(soyUnCompletableFuture, (anterior, actual) -> anterior + actual)
                    .thenApply(String::toUpperCase)
                    .join();
        
        });
    }

    public String demo3LlamadasAsyncLog() {
        return Utiles.timer(() -> {
            CompletableFuture<String> hello = CompletableFuture.supplyAsync(CommonsHolaMundo::hola);
            CompletableFuture<String> world = CompletableFuture.supplyAsync(CommonsHolaMundo::mundo);
            CompletableFuture<String> soyUnCompletableFuture = CompletableFuture.supplyAsync(() -> {
                Utiles.sleep(Duration.ofMillis(1000));
                return " Soy un Completable future!";
            });
    
            return hello
                    // .thenCombine(world, (h, w) -> h + w) // (primero, segundo)
                    .thenCombine(world, (h, w) -> {
                        log.info("thenCombine h/w ");
                        return h + w;
                    }) // (primero, segundo)
                    //.thenCombine(soyUnCompletableFuture, (anterior, actual) -> anterior + actual)
                    .thenCombine(soyUnCompletableFuture, (previous, current) -> {
                        log.info("thenCombine , anterior/actual");
                        return previous + current;
                    })
                    //.thenApply(String::toUpperCase)
                    .thenApply(s -> {
                        log.info("thenApply");
                        return s.toUpperCase();
                    })
                    .join();

        });
    }

    public String demo3LlamadasAsyncLogAsync() {
        return Utiles.timer(() -> {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(CommonsHolaMundo::hola);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(CommonsHolaMundo::mundo);
        CompletableFuture<String> soyUnCompletableFuture = CompletableFuture.supplyAsync(() -> {
            Utiles.sleep(Duration.ofMillis(1000));
            return " Soy un Completable future!";
        });

        return hello
                // .thenCombine(world, (h, w) -> h + w) // (primero, segundo)
                .thenCombineAsync(world, (h, w) -> {
                    log.info("thenCombine h/w ");
                    return h + w;
                }) // (primero, segundo)
                //.thenCombine(soyUnCompletableFuture, (anterior, actual) -> anterior + actual)
                .thenCombineAsync(soyUnCompletableFuture, (previous, current) -> {
                    CommonsHolaMundo.hola();
                    log.info("thenCombine , anterior/actual");
                    return previous + current;
                })
                //.thenApply(String::toUpperCase)
                .thenApplyAsync(s -> {
                    CommonsHolaMundo.hola();
                    log.info("thenApply");
                    return s.toUpperCase();
                })
                .join();

        });
    }


    public String demo3LlamadasAsyncExecutor() {

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        return Utiles.timer(() -> {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(CommonsHolaMundo::hola, executorService);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(CommonsHolaMundo::mundo, executorService);

        CompletableFuture<String> soyUnCompletableFuture = CompletableFuture.supplyAsync(() -> {
            Utiles.sleep(Duration.ofMillis(1000));
            return " Soy un Completable future!";
        }, executorService);

        return hello
                // .thenCombine(world, (h, w) -> h + w) // (primero, segundo)
                .thenCombine(world, (h, w) -> {
                    log.info("thenCombine h/w ");
                    return h + w;
                }) // (primero, segundo)
                //.thenCombine(soyUnCompletableFuture, (anterior, actual) -> anterior + actual)
                .thenCombine(soyUnCompletableFuture, (previous, current) -> {
                    log.info("thenCombine , anterior/actual");
                    return previous + current;
                })
                //.thenApply(String::toUpperCase)
                .thenApply(s -> {
                    log.info("thenApply");
                    return s.toUpperCase();
                })
                .join();
        });

    }

    public String demo3LlamadasAsyncLogExecutorAsync() {

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        return Utiles.timer(() -> {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(CommonsHolaMundo::hola, executorService);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(CommonsHolaMundo::mundo, executorService);

        CompletableFuture<String> soyUnCompletableFuture = CompletableFuture.supplyAsync(() -> {
            // Utiles.sleep(Duration.ofMillis(1000));
            return " Soy un Completable future!";
        }, executorService);

        return hello
                // .thenCombine(world, (h, w) -> h + w) // (primero, segundo)
                .thenCombineAsync(world, (h, w) -> {
                    log.info("thenCombine h/w ");
                    return h + w;
                }, executorService) // (primero, segundo)

                /*  .thenCombineAsync(world, (h, w) -> {
                      log.info("thenCombine h/w ");
                      return h + w;
                  }) // Sin executor como entrada */
                //.thenCombine(soyUnCompletableFuture, (anterior, actual) -> anterior + actual)
                .thenCombineAsync(soyUnCompletableFuture, (previous, current) -> {
                    log.info("thenCombine , anterior/actual");
                    return previous + current;
                }, executorService)
                //.thenApply(String::toUpperCase)
                .thenApply(s -> {
                    log.info("thenApply");
                    return s.toUpperCase();
                })
                .join();

        });
    }


    public String demo4LlamadasAsync() {
        return Utiles.timer(() -> {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(CommonsHolaMundo::hola);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(CommonsHolaMundo::mundo);
        CompletableFuture<String> soyUnCompletableFuture = CompletableFuture.supplyAsync(() -> {
            Utiles.sleep(Duration.ofMillis(1000));
            return " Soy un Completable future!";
        });
        CompletableFuture<String> byeCompletableFuture = CompletableFuture.supplyAsync(() -> {
            Utiles.sleep(Duration.ofMillis(1000));
            return "Adios";
        });


        return hello
                .thenCombine(world, (h, w) -> h + w) // (primero, segundo)
                .thenCombine(soyUnCompletableFuture, (anterior, actual) -> anterior + actual)
                .thenCombine(byeCompletableFuture, (anterior, actual) -> anterior + actual)
                .thenApply(String::toUpperCase)
                .join();

        });
    }

    public CompletableFuture<String> demoThenCompose() {
        CompletableFuture<String> helloWorldFuture = CompletableFuture.supplyAsync(CommonsHolaMundo::hola)
                .thenCompose(CommonsHolaMundo::mundoFuture)
                //.thenApply(previous -> helloWorldService.worldFuture(previous))
                .thenApply(String::toUpperCase);

        return helloWorldFuture;

    }

    public String demoAllOf() {
        return Utiles.timer(() -> {
            CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
                Utiles.sleep(Duration.ofMillis(1000));
                return "Hola";
            });

            CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
                Utiles.sleep(Duration.ofMillis(2000));
                return " Mundo";
            });

            List<CompletableFuture<String>> cfList = List.of(cf1, cf2);
            CompletableFuture<Void> cfAllOf = CompletableFuture.allOf(cfList.toArray(new CompletableFuture[]{}));
            return cfAllOf.thenApply(v -> cfList.stream()
                                                .map(CompletableFuture::join)
                                                .collect(joining())).join();
        });
    }

    public String demoAnyOf() {
        return Utiles.timer(() -> {

        CompletableFuture<String> db = CompletableFuture.supplyAsync(() -> {
            Utiles.sleep(Duration.ofMillis(1000));
            log.info("response desde db");
            return "Hola Mundo";
        });

        CompletableFuture<String> restApi = CompletableFuture.supplyAsync(() -> {
            Utiles.sleep(Duration.ofMillis(2000));
            log.info("response desde api rest");
            return "Hola Mundo";
        });

        CompletableFuture<String> soapApi = CompletableFuture.supplyAsync(() -> {
            Utiles.sleep(Duration.ofMillis(3000));
            log.info("response desde api soap");
            return "Hola Mundo";
        });

        List<CompletableFuture<String>> cfList = List.of(db, restApi, soapApi);
        CompletableFuture<Object> cfAllOf = CompletableFuture.anyOf(cfList.toArray(new CompletableFuture[cfList.size()]));
        return (String) cfAllOf.thenApply(v -> {
            if (v instanceof String) {
                return v;
            }
            return null;
        }).join();

        });
    }


    public String demoHolaMundo1() {

        return CompletableFuture.supplyAsync(CommonsHolaMundo::holaMundo)//  runs this in a common fork-join pool
                .thenApply(String::toUpperCase)
                .join();

    }

    public CompletableFuture<String> demoComplete(String input) {

        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        completableFuture = completableFuture
                .thenApply(String::toUpperCase)
                .thenApply((result) -> result.length() + " - " + result);

        completableFuture.complete(input);

        return completableFuture;

    }

    public static void main(String[] args) {
        CompletableFuture.supplyAsync(CommonsHolaMundo::holaMundo) //  runs this in a common fork-join pool
                .thenApply(String::toUpperCase)
                .thenAccept((result) -> {
                    log.info("resultado " + result);
                })
                .join();

        log.info("demo finalizada");
        Utiles.sleep(Duration.ofMillis(2000));
    }

}
