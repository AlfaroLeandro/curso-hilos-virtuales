package demo07.agregador;

import demo07.servicioexterno.ArticuloCliente;

import java.util.concurrent.ExecutorService;

public class AgregadorServicio {

    private final ExecutorService executorService;

    public AgregadorServicio(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public ArticuloDTO getArticulo(int id) throws Exception {
        var product = executorService.submit(() -> ArticuloCliente.getArticulo(id));
        var precio = executorService.submit(() -> ArticuloCliente.getPrecio(id));
        return new ArticuloDTO(id, product.get(), precio.get());
    }

}
