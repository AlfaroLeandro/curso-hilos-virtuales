package demo04;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Utiles;

import java.time.Duration;

public class PlanificacionCooperativaDemo {
        private static final Logger log = LoggerFactory.getLogger(PlanificacionCooperativaDemo.class);

        static {
            System.setProperty("jdk.virtualThreadScheduler.parallelism", "1");
            System.setProperty("jdk.virtualThreadScheduler.maxPoolSize", "1");
        }

        public static void main(String[] args) {

            var builder = Thread.ofVirtual();
            var t1 = builder.unstarted(() -> demo(1));
            var t2 = builder.unstarted(() -> demo(2));
            var t3 = builder.unstarted(() -> demo(3));
            t1.start();
            t2.start();
            t3.start();
            Utiles.sleep(Duration.ofSeconds(2));

        }

        private static void demo(int threadNumber){
            log.info("hilo-{} iniciado", threadNumber);
            for (int i = 0; i < 10; i++) {
                log.info("hilo-{} esta imprimiendo {}. Thread: {}", threadNumber, i, Thread.currentThread());
                Thread.yield(); // just for demo purposes
            }
            log.info("hilo-{} finalizado", threadNumber);
        }


    }

}
