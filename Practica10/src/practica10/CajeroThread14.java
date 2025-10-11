package practica10;

import static lpoo.utils.Validar.*;

public class CajeroThread14 extends Thread{
    private final CuentaBancaria cuenta;

    public CajeroThread14(CuentaBancaria cuenta, String nombre) {
        super(nombre);
        this.cuenta = cuenta;
    }

    @Override
    public void run() {
        print(getName() + " inicia transacciones.");
        try {
            cuenta.depositar(300);
            Thread.sleep(50);
            cuenta.retirar(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            print(getName() + " fue interrumpido.");
        }
        print(getName() + " termina transacciones. Saldo final: " + cuenta.getSaldo());
    }
}
