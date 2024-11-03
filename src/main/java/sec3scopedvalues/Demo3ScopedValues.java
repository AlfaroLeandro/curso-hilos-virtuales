package sec3scopedvalues;

import util.Utiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.UUID;

/*
    ScopedValues - PREVIEW feature
 */
public class Demo3ScopedValues {
    private static final Logger log = LoggerFactory.getLogger(Demo3ScopedValues.class);
    private static final ScopedValue<String> SESSION_TOKEN = ScopedValue.newInstance();

    public static void main(String[] args) {

        log.info("isBound={}", SESSION_TOKEN.isBound());
        log.info("valor={}", SESSION_TOKEN.orElse("valor por default"));

        Thread.ofVirtual().name("1").start(Demo3ScopedValues::procesarPeticion);
        Thread.ofVirtual().name("2").start(Demo3ScopedValues::procesarPeticion);

        Utiles.sleep(Duration.ofSeconds(1));

    }

    /**
     * El siguiente codigo es para demostrar el flujo de trabajo
     */

    private static void procesarPeticion(){
        var token = autenticar();

        ScopedValue.runWhere(SESSION_TOKEN, token, Demo3ScopedValues::controlador);

        //controlador();
    }

    private static String autenticar(){
        var token = UUID.randomUUID().toString();
        log.info("token={}", token);
        return token;
    }

    // @Principal
    private static void controlador(){
        log.info("controlador: {}", SESSION_TOKEN.get());
        servicio();
    }

    private static void servicio(){
        log.info("servicio: {}", SESSION_TOKEN.get());
        ScopedValue.runWhere(SESSION_TOKEN,
                        "nuevo-token-" + Thread.currentThread().getName(),
                              Demo3ScopedValues::servicioExterno);
    }

    // Este cliente llama a un servicio externo
    private static void servicioExterno() {
        log.info("Preparando peticion Http con token: {}", SESSION_TOKEN.get());
    }

}
