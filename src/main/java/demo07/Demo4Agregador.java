package demo07;

import demo07.aggregator.AgregadorServicio;
import demo07.aggregator.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class Demo4Agregador {

    private static final Logger log = LoggerFactory.getLogger(Demo4Agregador.class);

    public static void main(String[] args) throws Exception {

        // beans / singletons
        var executor = Executors.newVirtualThreadPerTaskExecutor();
        var aggregator = new AgregadorServicio(executor);

        var futures = IntStream.rangeClosed(1, 50)
                               .mapToObj(id -> executor.submit(() -> aggregator.getProductDto(id))).toList();
        var list = futures.stream()
                          .map(Demo4Agregador::toProductDto)
                          .toList();

        log.info("list: {}", list);

    }

    private static ProductDto toProductDto(Future<ProductDto> future){
        try {
            return future.get();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
