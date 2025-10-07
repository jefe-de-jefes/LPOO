package practica7;

public class Saldo10InsuficienteException extends ExceptionSegobiaBase{
    public Saldo10InsuficienteException(double saldo, double retiro){
        super("Fondos insuficientes. Intento de retiro de " + retiro + ". Tienes un saldo actual de " + saldo + ".");
    }
}
