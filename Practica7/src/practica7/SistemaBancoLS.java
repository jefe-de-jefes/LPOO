package practica7;


import java.util.logging.*;
import java.io.IOException;

class Cuenta {
    String matricula;
    double saldo;
    public Cuenta(String m, double s) { this.matricula = m; this.saldo = s; }
    public String getMatricula() { return matricula; }
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
}

public class SistemaBancoLS {
    
    private static final Logger LOGGER = Logger.getLogger(SistemaBancoLS.class.getName());
    static {
        try {
            FileHandler fileHandler = new FileHandler("banco_LS_transacciones.log", true); 
            fileHandler.setFormatter(new SimpleFormatter());
            
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.INFO); 
            

            LOGGER.log(Level.INFO, "--- Logger de Practica 7 listo para registrar errores. ---");
        } catch (IOException e) {
            System.err.println("Error al configurar el FileHandler: " + e.getMessage());
        }
    }
    private static final Cuenta[] BASE_DATOS = new Cuenta[]{
        new Cuenta("1234567", 1500.00),
        new Cuenta("6543210", 500.00), 
        new Cuenta("9876543", 5000.00)
    };
    
    public void retiro(String matricula, double monto) 
        throws Usuario7528NoEncontradoException, Matricula14InvalidaException, Saldo10InsuficienteException {

        if (!matricula.matches("\\d{7}")) {
            throw new Matricula14InvalidaException(matricula);
        }

        Cuenta cuenta = null;
        for (Cuenta c : BASE_DATOS) {
            if (c.getMatricula().equals(matricula)) {
                cuenta = c;
                break;
            }
        }
        if (cuenta == null) {
            throw new Usuario7528NoEncontradoException(matricula);
        }

        if (cuenta.getSaldo() < monto) {
            throw new Saldo10InsuficienteException(cuenta.getSaldo(), monto);
        }

        cuenta.setSaldo(cuenta.getSaldo() - monto);
        LOGGER.log(Level.INFO, "Retiro exitoso. Matricula: {0}, Monto: {1}. Saldo actual de: {2}", 
                   new Object[]{matricula, monto, cuenta.getSaldo()});
    }

    public void probarOperaciones() {
        try {
            retiro("6543210", 1000.00); 
        } catch (Saldo10InsuficienteException e) {
            LOGGER.log(Level.WARNING, "ERROR DE TRANSACCION", e);
            System.err.println("ERROR: " + e.getMessage());
        } catch (ExceptionSegobiaBase e) {
            LOGGER.log(Level.SEVERE, "ERROR DE APLICACION", e);
            System.err.println("ERROR: " + e.getMessage());
        }
        
        try {
            retiro("1B567", 100.00);
        } catch (Matricula14InvalidaException e){
            LOGGER.log(Level.WARNING, "ERROR DE FORMATO", e);
            System.err.println("ERROR: " + e.getMessage());
        } catch (ExceptionSegobiaBase e) {
            LOGGER.log(Level.SEVERE, "ERROR DE APLICACION", e);
            System.err.println("ERROR: " + e.getMessage());
        }

        try {
            retiro("9637418", 100.00);
        } catch (Usuario7528NoEncontradoException e){
            LOGGER.log(Level.WARNING, "ERROR DE CAPA 8", e);
            System.err.println("ERROR: " + e.getMessage());
        } catch (ExceptionSegobiaBase e) {
            LOGGER.log(Level.SEVERE, "ERROR DE APLICACION", e);
            System.err.println("ERROR: " + e.getMessage());
        }

    }
    
    public static void main(String[] args) {
        new SistemaBancoLS().probarOperaciones();
    }
}
