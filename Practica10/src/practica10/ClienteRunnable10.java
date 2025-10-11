package practica10;

import static lpoo.utils.Validar.*;

public class ClienteRunnable10 implements Runnable {
    private final CuentaBancaria cuenta;
    private final String nombre;

    public ClienteRunnable10(CuentaBancaria cuenta, String nombre) {
        this.cuenta = cuenta;
        this.nombre = nombre;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(this.nombre);
        print(nombre + " inicia transacciones.");

        try {

            cuenta.retirar(50);
            Thread.sleep(100); 

            cuenta.retirar(25);
            Thread.sleep(50);

            cuenta.depositar(100);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            print(nombre + " fue interrumpido.");
        }

        print(nombre + " termina transacciones. Saldo final: $" + cuenta.getSaldo());
    }
}
