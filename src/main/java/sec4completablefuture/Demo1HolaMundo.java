package sec4completablefuture;

import sec4completablefuture.servicio.HolaMundoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.stream.Collectors.joining;

public class Demo1HolaMundo {

    private static final Logger log = LoggerFactory.getLogger(Demo1HolaMundo.class);
    private HolaMundoServicio hms;
    
    
    public Demo1HolaMundo(HolaMundoServicio hms) {
        this.hms = hms;
    }
    
    public CompletableFuture<String> demo() {
        return CompletableFuture.supplyAsync(hms::holaMundo)//  common fork-join pool
                                .thenApply(String::toUpperCase);
    }

    public CompletableFuture<String> demoThenApply() {
        return CompletableFuture.supplyAsync(hms::holaMundo)//  common fork-join pool
                                .thenApply(String::toUpperCase)
                                .thenApply((s) -> s.length() + " - " + s);
    }

    public String demoLlamadasAsync() {
        return Utiles.timer(() -> {
                CompletableFuture<String> hola = CompletableFuture.supplyAsync(hms::hola);
                CompletableFuture<String> mundo = CompletableFuture.supplyAsync(hms::mundo);
    
                return hola
                    .thenCombine(mundo, (h, w) -> h + w) // (primero,segundo)
                    .thenApply(String::toUpperCase)
                    .join();
        });
    }


    public String demo3LlamadasAsync() {
        return Utiles.timer(() -> {
            CompletableFuture<String> hola = CompletableFuture.supplyAsync(hms::hola);
            CompletableFuture<String> mundo = CompletableFuture.supplyAsync(hms::mundo);
            CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
                Utiles.sleep(Duration.ofMillis(1000));
                return " Soy un Completable future!";
            });

            return hola
                    .thenCombine(mundo, (h, w) -> h + w) // (primero, segundo)
                    .thenCombine(cf, (anterior, actual) -> anterior + actual)
                    .thenApply(String::toUpperCase)
                    .join();
        
        });
    }

    public String demo3LlamadasAsyncLog() {
        return Utiles.timer(() -> {
            CompletableFuture<String> hola = CompletableFuture.supplyAsync(hms::hola);
            CompletableFuture<String> mundo = CompletableFuture.supplyAsync(hms::mundo);
            CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
                Utiles.sleep(Duration.ofMillis(1000));
                return " Soy un Completable future!";
            });
    
            return hola
                    // .thenCombine(mundo, (h, w) -> h + w) // (primero, segundo)
                    .thenCombine(mundo, (h, w) -> {
                        log.info("thenCombine h/w ");
                        return h + w;
                    }) // (primero, segundo)
                    //.thenCombine(cf, (anterior, actual) -> anterior + actual)
                    .thenCombine(cf, (previous, current) -> {
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
        CompletableFuture<String> hola = CompletableFuture.supplyAsync(hms::hola);
        CompletableFuture<String> mundo = CompletableFuture.supplyAsync(hms::mundo);
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            Utiles.sleep(Duration.ofMillis(1000));
            return " Soy un Completable future!";
        });

        return hola
                // .thenCombine(mundo, (h, w) -> h + w) // (primero, segundo)
                .thenCombineAsync(mundo, (h, w) -> {
                    log.info("thenCombine h/w ");
                    return h + w;
                }) // (primero, segundo)
                //.thenCombine(cf, (anterior, actual) -> anterior + actual)
                .thenCombineAsync(cf, (previous, current) -> {
                    hms.hola();
                    log.info("thenCombine , anterior/actual");
                    return previous + current;
                })
                //.thenApply(String::toUpperCase)
                .thenApplyAsync(s -> {
                    hms.hola();
                    log.info("thenApply");
                    return s.toUpperCase();
                })
                .join();

        });
    }


    public String demo3LlamadasAsyncExecutor() {

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        return Utiles.timer(() -> {
        CompletableFuture<String> hola = CompletableFuture.supplyAsync(hms::hola, executorService);
        CompletableFuture<String> mundo = CompletableFuture.supplyAsync(hms::mundo, executorService);

        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            Utiles.sleep(Duration.ofMillis(1000));
            return " Soy un Completable future!";
        }, executorService);

        return hola
                // .thenCombine(mundo, (h, w) -> h + w) // (primero, segundo)
                .thenCombine(mundo, (h, w) -> {
                    log.info("thenCombine h/w ");
                    return h + w;
                }) // (primero, segundo)
                //.thenCombine(cf, (anterior, actual) -> anterior + actual)
                .thenCombine(cf, (previous, current) -> {
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
        CompletableFuture<String> hola = CompletableFuture.supplyAsync(hms::hola, executorService);
        CompletableFuture<String> mundo = CompletableFuture.supplyAsync(hms::mundo, executorService);

        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            // Utiles.sleep(Duration.ofMillis(1000));
            return " Soy un Completable future!";
        }, executorService);

        return hola
                // .thenCombine(mundo, (h, w) -> h + w) // (primero, segundo)
                .thenCombineAsync(mundo, (h, w) -> {
                    log.info("thenCombine h/w ");
                    return h + w;
                }, executorService) // (primero, segundo)

                /*  .thenCombineAsync(mundo, (h, w) -> {
                      log.info("thenCombine h/w ");
                      return h + w;
                  }) // Sin executor como entrada */
                //.thenCombine(cf, (anterior, actual) -> anterior + actual)
                .thenCombineAsync(cf, (previous, current) -> {
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
        CompletableFuture<String> hola = CompletableFuture.supplyAsync(hms::hola);
        CompletableFuture<String> mundo = CompletableFuture.supplyAsync(hms::mundo);
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            Utiles.sleep(Duration.ofMillis(1000));
            return " Soy un Completable future!";
        });
        CompletableFuture<String> byeCompletableFuture = CompletableFuture.supplyAsync(() -> {
            Utiles.sleep(Duration.ofMillis(1000));
            return "Adios";
        });


        return hola
                .thenCombine(mundo, (h, w) -> h + w) // (primero, segundo)
                .thenCombine(cf, (anterior, actual) -> anterior + actual)
                .thenCombine(byeCompletableFuture, (anterior, actual) -> anterior + actual)
                .thenApply(String::toUpperCase)
                .join();

        });
    }

    public CompletableFuture<String> demoThenCompose() {
        CompletableFuture<String> holaMundoFuture = CompletableFuture.supplyAsync(hms::hola)
                .thenCompose(hms::mundoFuture)
                //.thenApply(previous -> helloWorldService.worldFuture(previous))
                .thenApply(String::toUpperCase);

        return holaMundoFuture;

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

        return CompletableFuture.supplyAsync(hms::holaMundo)//   common fork-join pool
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
//        CompletableFuture.supplyAsync(this::holaMundo) //   common fork-join pool
//                .thenApply(String::toUpperCase)
//                .thenAccept((result) -> {
//                    log.info("resultado " + result);
//                })
//                .join();

        log.info("demo finalizada");
        Utiles.sleep(Duration.ofMillis(2000));
    }

}
