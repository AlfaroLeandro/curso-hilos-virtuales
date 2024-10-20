package demo08.agregador;

import demo08.servicioexterno.ArticuloCliente;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class AgregadorServicio {

    private final ExecutorService executorService;

    public AgregadorServicio(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public ArticuloDTO getArticulo(int id) {
        var product = executorService.submit(() -> ArticuloCliente.getArticulo(id));
        var rating = executorService.submit(() -> ArticuloCliente.getPrecio(id));
        try {
            return new ArticuloDTO(id, product.get(), rating.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
