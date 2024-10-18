package demo07;

import demo07.externalservice.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Lec07ScheduledExecutorWithVirtualThreads {

    private static final Logger log = LoggerFactory.getLogger(Lec07ScheduledExecutorWithVirtualThreads.class);

    public static void main(String[] args) {
        scheduled();
    }

    // To schedule tasks periodically
    private static void scheduled(){
        var scheduler = Executors.newSingleThreadScheduledExecutor();
        var executor = Executors.newVirtualThreadPerTaskExecutor();
        try(scheduler; executor){
            scheduler.scheduleAtFixedRate(() -> {
                executor.submit(() -> printProductInfo(1));
            }, 0, 3, TimeUnit.SECONDS);

            Utiles.sleep(Duration.ofSeconds(15));
        }
    }

    private static void printProductInfo(int id){
        log.info("{} => {}", id, Client.getProducto(id));
    }

}
