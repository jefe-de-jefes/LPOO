package practica10;

import static lpoo.utils.Validar.*;
import java.util.LinkedList;
import java.util.Queue;

public class BufferSegobia<T>{
    
    private final int CAPACIDAD = 28; 
    private final Queue<T> buffer = new LinkedList<>(); 

    public BufferSegobia() {
        print("\nBufferSegobia inicializado con capacidad: " + CAPACIDAD);
    }


    public synchronized void producir(T valor) throws InterruptedException {
        while (buffer.size() == CAPACIDAD) {
            print("**BufferSegobia lleno. Productor " + Thread.currentThread().getName() + " ESPERA**");
            wait(); 
        }

        buffer.offer(valor);
        print("Productor " + Thread.currentThread().getName() + " produjo: " + valor + " | Tamano: " + buffer.size());

        notifyAll(); // Notifica a todos los hilos bloqueados que pueden reanudar
    }


    public synchronized T consumir() throws InterruptedException {
        while (buffer.isEmpty()) {
            print("**BufferSegobia vacio. Consumidor " + Thread.currentThread().getName() + " ESPERA**");
            wait(); 
        }

        T valor = buffer.poll();
        print("Consumidor " + Thread.currentThread().getName() + " consumio: " + valor + " | Tamano: " + buffer.size());

        notifyAll(); // Notifica a todos los hilos bloqueados que pueden reanudar
        return valor;
    }
}
