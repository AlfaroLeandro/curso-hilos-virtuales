package demo07.concurrencylimit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.*;

public class ConcurrenciaLimitador implements AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(ConcurrenciaLimitador.class);

    private final ExecutorService executor;
    private final Semaphore semaforo;
    private final Queue<Callable<?>> queue;

    public ConcurrenciaLimitador(ExecutorService executor, int limit) {
        this.executor = executor;
        this.semaforo = new Semaphore(limit);
        this.queue = new ConcurrentLinkedQueue<>();
    }

    public <T> Future<T> submit(Callable<T> callable){
        this.queue.add(callable);
        return executor.submit(() -> ejecutarTarea() );
    }

    private <T> T ejecutarTarea(){
        try{
            semaforo.acquire();
            return (T) this.queue.poll().call();
        }catch (Exception e){
            log.error("error", e);
        }finally {
            semaforo.release();
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        this.executor.close();
    }
}
