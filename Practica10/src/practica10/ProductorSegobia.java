package practica10;

import static lpoo.utils.Validar.*;

public class ProductorSegobia implements Runnable {
    private final BufferSegobia<Integer> buffer;

    public ProductorSegobia(BufferSegobia<Integer> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            int valor = (int)(Math.random() * 100);
            try {
                buffer.producir(valor);
                Thread.sleep((int)(Math.random() * 100));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                print("Productor interrumpido: " + Thread.currentThread().getName());
            }
        }
    }
}
