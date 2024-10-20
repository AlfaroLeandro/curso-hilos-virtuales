package demo09;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;
import java.util.UUID;
/*
    Demo simple de Thread Local
 */
public class Demo1ThreadLocal {

    private static final Logger log = LoggerFactory.getLogger(Demo1ThreadLocal.class);
    private static final ThreadLocal<String> SESSION_TOKEN = new ThreadLocal<>();

    public static void main(String[] args) {

        Thread.ofVirtual().name("1").start(Demo1ThreadLocal::procesarPeticion);
        Thread.ofVirtual().name("2").start(Demo1ThreadLocal::procesarPeticion);

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
        log.info("service: {}", SESSION_TOKEN.get());
        servicioExterno();
    }

    // Este cliente llama a un servicio externo
    private static void servicioExterno(){
        log.info("Preparando peticion Http con token: {}", SESSION_TOKEN.get());
    }

}
