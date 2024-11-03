package demo08.agregador;

import demo08.cliente.ArticuloCliente;

import java.util.concurrent.ExecutorService;

public class AgregadorServicio {

    private final ExecutorService executorService;

    public AgregadorServicio(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public ArticuloDTO getArticulo(int id) {
        var product = executorService.submit(() -> ArticuloCliente.getArticulo(id));
        var precio = executorService.submit(() -> ArticuloCliente.getPrecio(id));
        try {
            return new ArticuloDTO(id, product.get(), precio.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
