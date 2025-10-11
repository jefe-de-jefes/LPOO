package practica10;

import static lpoo.utils.Validar.*;

public class ConsumidorSegobia implements Runnable {
    private final BufferSegobia<Integer> buffer;

    public ConsumidorSegobia(BufferSegobia<Integer> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            try {
                buffer.consumir();
                Thread.sleep((int)(Math.random() * 150));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                print("Consumidor interrumpido: " + Thread.currentThread().getName());
            }
        }
    }
}
