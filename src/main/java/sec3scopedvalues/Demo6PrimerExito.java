package sec3scopedvalues;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.StructuredTaskScope;

/*
    Demo de concurrencia estructurada cuando nosotros queremos cancelar todas las subtareas cuando la primera termina exitosamente
 */
public class Demo6PrimerExito {

    private static final Logger log = LoggerFactory.getLogger(Demo6PrimerExito.class);

    public static void main(String[] args) {

        try(var tareaScope = new StructuredTaskScope.ShutdownOnSuccess<>()){
            var subtarea1 = tareaScope.fork(CommonsDemo09::tareaError);
            var subtarea2 = tareaScope.fork(CommonsDemo09::servicioCalcularPrecioArticulo);

            tareaScope.join();

            log.info("subtarea1 state: {}", subtarea1.state());
            log.info("subtarea2 state: {}", subtarea2.state());

            log.info("resultado subtarea: {}", tareaScope.result(ex -> new RuntimeException("todos fallaron")));


        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

}
