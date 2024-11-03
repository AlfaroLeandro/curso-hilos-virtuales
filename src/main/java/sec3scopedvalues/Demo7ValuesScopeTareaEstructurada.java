package sec3scopedvalues;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.StructuredTaskScope;

public class Demo7ValuesScopeTareaEstructurada {

    private static final Logger log = LoggerFactory.getLogger(Demo7ValuesScopeTareaEstructurada.class);
    private static final ScopedValue<String> SESSION_TOKEN = ScopedValue.newInstance();

    public static void main(String[] args) {

        ScopedValue.runWhere(SESSION_TOKEN, "token-123", Demo7ValuesScopeTareaEstructurada::task);

    }

    private static void task(){
        try(var tareaScope = new StructuredTaskScope<>()){

            log.info("token: {}", SESSION_TOKEN.get());

            var subtarea1 = tareaScope.fork(CommonsDemo09::servicioCalcularPrecioArticulo);
            var subtarea2 = tareaScope.fork(CommonsDemo09::servicioCalcularPrecioArticulo2);

            tareaScope.join();

            log.info("subtarea1 estado: {}", subtarea1.state());
            log.info("subtarea2 estado: {}", subtarea2.state());

            log.info("subtarea1 resultado: {}", subtarea1.get());
            log.info("subtarea2 resultado: {}", subtarea2.get());

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


}
