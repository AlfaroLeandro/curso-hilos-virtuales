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

    public static CompletableFuture<String> helloWorld() {

        return CompletableFuture.supplyAsync(CommonsHolaMundo::holaMundo)//  runs this in a common fork-join pool
                                .thenApply(String::toUpperCase);
    }

    public CompletableFuture<String> helloWorld_withSize() {
        return CompletableFuture.supplyAsync(CommonsHolaMundo::holaMundo)//  runs this in a common fork-join pool
                                .thenApply(String::toUpperCase)
                                .thenApply((s) -> s.length() + " - " + s);
    }

    public String helloWorld_multiple_async_calls() {
        return Utiles.timer(() -> {
                CompletableFuture<String> hello = CompletableFuture.supplyAsync(CommonsHolaMundo::hola);
                CompletableFuture<String> world = CompletableFuture.supplyAsync(CommonsHolaMundo::mundo);
    
                return hello
                    .thenCombine(world, (h, w) -> h + w) // (primero,segundo)
                    .thenApply(String::toUpperCase)
                    .join();
        });
    }


    public String helloWorld_3_async_calls() {
        return Utiles.timer(() -> {
            CompletableFuture<String> hello = CompletableFuture.supplyAsync(CommonsHolaMundo::hola);
            CompletableFuture<String> world = CompletableFuture.supplyAsync(CommonsHolaMundo::mundo);
            CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
                Utiles.sleep(Duration.ofMillis(1000));
                return " HI CompletableFuture!";
            });

            return hello
                    .thenCombine(world, (h, w) -> h + w) // (first,second)
                    .thenCombine(hiCompletableFuture, (previous, current) -> previous + current)
                    .thenApply(String::toUpperCase)
                    .join();
        
        });
    }

    public String helloWorld_3_async_calls_log() {
        return Utiles.timer(() -> {
            CompletableFuture<String> hello = CompletableFuture.supplyAsync(CommonsHolaMundo::hola);
            CompletableFuture<String> world = CompletableFuture.supplyAsync(CommonsHolaMundo::mundo);
            CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
                Utiles.sleep(Duration.ofMillis(1000));
                return " HI CompletableFuture!";
            });
    
            return hello
                    // .thenCombine(world, (h, w) -> h + w) // (first,second)
                    .thenCombine(world, (h, w) -> {
                        log.info("thenCombine h/w ");
                        return h + w;
                    }) // (first,second)
                    //.thenCombine(hiCompletableFuture, (previous, current) -> previous + current)
                    .thenCombine(hiCompletableFuture, (previous, current) -> {
                        log.info("thenCombine , previous/current");
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

    public String helloWorld_3_async_calls_log_async() {
        return Utiles.timer(() -> {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(CommonsHolaMundo::hola);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(CommonsHolaMundo::mundo);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            Utiles.sleep(Duration.ofMillis(1000));
            return " HI CompletableFuture!";
        });

        return hello
                // .thenCombine(world, (h, w) -> h + w) // (first,second)
                .thenCombineAsync(world, (h, w) -> {
                    log.info("thenCombine h/w ");
                    return h + w;
                }) // (first,second)
                //.thenCombine(hiCompletableFuture, (previous, current) -> previous + current)
                .thenCombineAsync(hiCompletableFuture, (previous, current) -> {
                    CommonsHolaMundo.hola();
                    log.info("thenCombine , previous/current");
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


    public String helloWorld_3_async_calls_custom_threadPool() {

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        return Utiles.timer(() -> {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(CommonsHolaMundo::hola, executorService);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(CommonsHolaMundo::mundo, executorService);

        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            Utiles.sleep(Duration.ofMillis(1000));
            return " HI CompletableFuture!";
        }, executorService);

        return hello
                // .thenCombine(world, (h, w) -> h + w) // (first,second)
                .thenCombine(world, (h, w) -> {
                    log.info("thenCombine h/w ");
                    return h + w;
                }) // (first,second)
                //.thenCombine(hiCompletableFuture, (previous, current) -> previous + current)
                .thenCombine(hiCompletableFuture, (previous, current) -> {
                    log.info("thenCombine , previous/current");
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

    public String helloWorld_3_async_calls_custom_threadpool_async() {

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        return Utiles.timer(() -> {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(CommonsHolaMundo::hola, executorService);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(CommonsHolaMundo::mundo, executorService);

        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            // Utiles.sleep(Duration.ofMillis(1000));
            return " HI CompletableFuture!";
        }, executorService);

        return hello
                // .thenCombine(world, (h, w) -> h + w) // (first,second)
                .thenCombineAsync(world, (h, w) -> {
                    log.info("thenCombine h/w ");
                    return h + w;
                }, executorService) // (first,second)

                /*  .thenCombineAsync(world, (h, w) -> {
                      log.info("thenCombine h/w ");
                      return h + w;
                  }) // with no executor service as an input*/
                //.thenCombine(hiCompletableFuture, (previous, current) -> previous + current)
                .thenCombineAsync(hiCompletableFuture, (previous, current) -> {
                    log.info("thenCombine , previous/current");
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


    public String helloWorld_4_async_calls() {
        return Utiles.timer(() -> {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(CommonsHolaMundo::hola);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(CommonsHolaMundo::mundo);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            Utiles.sleep(Duration.ofMillis(1000));
            return " HI CompletableFuture!";
        });
        CompletableFuture<String> byeCompletableFuture = CompletableFuture.supplyAsync(() -> {
            Utiles.sleep(Duration.ofMillis(1000));
            return " Bye!";
        });


        return hello
                .thenCombine(world, (h, w) -> h + w) // (first,second)
                .thenCombine(hiCompletableFuture, (previous, current) -> previous + current)
                .thenCombine(byeCompletableFuture, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();

        });
    }

    public CompletableFuture<String> helloWorld_thenCompose() {
        CompletableFuture<String> helloWorldFuture = CompletableFuture.supplyAsync(CommonsHolaMundo::hola)
                .thenCompose(CommonsHolaMundo::mundoFuture)
                //.thenApply(previous -> helloWorldService.worldFuture(previous))
                .thenApply(String::toUpperCase);

        return helloWorldFuture;

    }

    public String allOf() {
        return Utiles.timer(() -> {
            CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
                Utiles.sleep(Duration.ofMillis(1000));
                return "Hello";
            });

            CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
                Utiles.sleep(Duration.ofMillis(2000));
                return " World";
            });

            List<CompletableFuture<String>> cfList = List.of(cf1, cf2);
            CompletableFuture<Void> cfAllOf = CompletableFuture.allOf(cfList.toArray(new CompletableFuture[]{}));
            return cfAllOf.thenApply(v -> cfList.stream()
                    .map(CompletableFuture::join)
                    .collect(joining())).join();
        });
    }

    public String anyOf() {
        return Utiles.timer(() -> {

        CompletableFuture<String> db = CompletableFuture.supplyAsync(() -> {
            Utiles.sleep(Duration.ofMillis(1000));
            log.info("response from db");
            return "Hello World";
        });

        CompletableFuture<String> restApi = CompletableFuture.supplyAsync(() -> {
            Utiles.sleep(Duration.ofMillis(2000));
            log.info("response from restApi");
            return "Hello World";
        });

        CompletableFuture<String> soapApi = CompletableFuture.supplyAsync(() -> {
            Utiles.sleep(Duration.ofMillis(3000));
            log.info("response from soapApi");
            return "Hello World";
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


    public String helloWorld_1() {

        return CompletableFuture.supplyAsync(CommonsHolaMundo::holaMundo)//  runs this in a common fork-join pool
                .thenApply(String::toUpperCase)
                .join();

    }

    public CompletableFuture<String> complete(String input) {

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
                    log.info("result " + result);
                })
                .join();

        log.info("Done!");
        Utiles.sleep(Duration.ofMillis(2000));
    }

}
