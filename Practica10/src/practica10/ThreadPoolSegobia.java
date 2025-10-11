package practica10;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import static lpoo.utils.Validar.*;

public class ThreadPoolSegobia {

    private final int numHilos;
    private final List<Worker> hilos;
    private final Queue<Runnable> colaTareas;
    private volatile boolean activo = true;

    public ThreadPoolSegobia(int numHilos) {
        this.numHilos = numHilos;
        this.hilos = new LinkedList<>();
        this.colaTareas = new LinkedList<>();

        print("Creando pool de hilos con " + numHilos + " workers...");

        for (int i = 0; i < numHilos; i++) {
            Worker worker = new Worker("Worker-" + i);
            worker.start();
            hilos.add(worker);
        }
    }

    public synchronized void execute(Runnable tarea) {
        if (!activo) {
            throw new IllegalStateException("ThreadPool ya cerrado");
        }
        colaTareas.offer(tarea);
        notify(); // notifica a un worker disponible
    }

    public synchronized void shutdown() {
        activo = false;
        notifyAll(); // despertar todos los hilos
        for (Worker worker : hilos) {
            worker.interrupt();
        }
        print("ThreadPool cerrado.");
    }

    private class Worker extends Thread {
        public Worker(String nombre) {
            super(nombre);
        }

        @Override
        public void run() {
            while (activo || !colaTareas.isEmpty()) {
                Runnable tarea;
                synchronized (ThreadPoolSegobia.this) {
                    while (colaTareas.isEmpty() && activo) {
                        try {
                            ThreadPoolSegobia.this.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    if (!activo && colaTareas.isEmpty()) {
                        break; 
                    }

                    tarea = colaTareas.poll();
                }

                try {
                    if (tarea != null) {
                        print(getName() + " ejecutando tarea...");
                        tarea.run();
                    }
                } catch (RuntimeException e) {
                    print("Error ejecutando tarea: " + e.getMessage());
                }
            }
        }
    }
}
