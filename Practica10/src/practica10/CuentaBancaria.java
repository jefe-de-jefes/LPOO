package practica10;

import static lpoo.utils.Validar.*;

public class CuentaBancaria {
    private double saldo;
    private final String titular;
    private String cotitular1;
    private String cotitular2;

    public CuentaBancaria(double saldoInicial, String titular) {
        this.saldo = saldoInicial;
        this.titular = titular;
    }

    public synchronized void depositar(double cantidad) {
        print(Thread.currentThread().getName() + " >> Se realizara el deposito de: $" + cantidad);
        
        try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        
        this.saldo += cantidad;
        print(Thread.currentThread().getName() + " << Deposito realizado con exito. Saldo actual: $" + this.saldo);
    }

    public synchronized void retirar(double cantidad) {
        print(Thread.currentThread().getName() + " >> Se realizara un retiro de: $" + cantidad);
        
        if (this.saldo >= cantidad) {
            try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            this.saldo -= cantidad;
            System.out.println(Thread.currentThread().getName() + " << Retiro exitoso. Saldo actual: $" + this.saldo);
        } else {
            print(Thread.currentThread().getName() + " -- Fondos insuficientes. Saldo: $" + this.saldo);
        }
    }

    //cotitulares
    public synchronized void agregarCotitular1(String nombreCotitular){
        this.cotitular1 = nombreCotitular;
        return;
    }
    public synchronized String eliminarCotitular1(){
        String nameCo = this.cotitular1;
        this.cotitular1 = null;
        return nameCo;
    }

    public synchronized void agregarCotitular2(String nombreCotitular){
        this.cotitular2 = nombreCotitular;
        return;
    }
    public synchronized String eliminarCotitular2(){
        String nameCo = this.cotitular2;
        this.cotitular2 = null;
        return nameCo;
    }

    //getters
    public double getSaldo() {
        return saldo;
    }
    public String getTitular(){
        return this.titular;
    }
    public String getCotitular1(){
        return this.cotitular1;
    }
    public String getCotitular2(){
        return this.cotitular2;
    }
}
