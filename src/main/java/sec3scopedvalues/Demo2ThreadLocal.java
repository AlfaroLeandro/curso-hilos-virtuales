package sec3scopedvalues;

import util.Utiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.UUID;

/*
    Demo de Inheritable ThreadLocal
 */
public class Demo2ThreadLocal {

    private static final Logger log = LoggerFactory.getLogger(Demo2ThreadLocal.class);
    private static final ThreadLocal<String> SESSION_TOKEN = new InheritableThreadLocal<>();

    public static void main(String[] args) {

        Thread.ofVirtual().name("1").start(Demo2ThreadLocal::procesarPeticion);
        Thread.ofVirtual().name("2").start(Demo2ThreadLocal::procesarPeticion);

        Utiles.sleep(Duration.ofSeconds(1));

    }

    /**
     * El siguiente codigo es para demostrar el flujo de trabajo
     */


    private static void procesarPeticion(){
        autenticacion();
        controlador();
    }

    private static void autenticacion(){
        var token = UUID.randomUUID().toString();
        log.info("token={}", token);
        SESSION_TOKEN.set(token);
    }

    // @Principal
    private static void controlador(){
        log.info("controller: {}", SESSION_TOKEN.get());
        servicio();
    }

    private static void servicio(){
        log.info("servicio: {}", SESSION_TOKEN.get());
        var threadName = "hijo-" + Thread.currentThread().getName();
        Thread.ofVirtual().name(threadName).start(Demo2ThreadLocal::servicioExterno);
    }

    // Este cliente llama a un servicio externo
    private static void servicioExterno(){
        log.info("Preparando peticion Http con token: {}", SESSION_TOKEN.get());
    }

}
