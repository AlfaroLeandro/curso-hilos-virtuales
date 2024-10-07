package demo07.aggregator;

import demo07.externalservice.Client;

import java.util.concurrent.ExecutorService;

public class AgregadorServicio {

    private final ExecutorService executorService;

    public AgregadorServicio(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public ProductDto getProductDto(int id) throws Exception {
        var product = executorService.submit(() -> Client.getProducto(id));
        var rating = executorService.submit(() -> Client.getRating(id));
        return new ProductDto(id, product.get(), rating.get());
    }

}
