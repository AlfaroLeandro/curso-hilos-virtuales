package sec3scopedvalues;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.StructuredTaskScope;

/*
   Demo de concurrencia estructurada donde queremos cancelar todas las subtareas cuando una falla
 */
public class Demo5CancelarEnFallo {

    private static final Logger log = LoggerFactory.getLogger(Demo5CancelarEnFallo.class);

    public static void main(String[] args) {

        try(var tareaScope = new StructuredTaskScope.ShutdownOnFailure()){
            var subtarea1 = tareaScope.fork(CommonsDemo09::servicioCalcularPrecioArticulo);
            var subtarea2 = tareaScope.fork(CommonsDemo09::tareaError);

            tareaScope.join();
            tareaScope.throwIfFailed(ex -> new RuntimeException("algo salio mal"));

            log.info("subtarea1 estado: {}", subtarea1.state());
            log.info("subtarea2 estado: {}", subtarea2.state());

//            log.info("subtarea1 resultado: {}", subtarea1.get());
//            log.info("subtarea2 resultado: {}", subtarea2.get());

        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

}
