package practica3;

import static lpoo.utils.Validar.*;

public class CuentaBancaria1410{
    private static final int LONGITUD_NUM_CTA = 10;
    private static final int LONGITUD_NUM_TDD = 16;
    private static final int LONGITUD_NUM_CLABE = 18;
    private final String numCuenta; //el numero de cuenta en un banco nunca cambia por eso es final
    private String numTdd;
    private String clabe;
    private double saldo;
    private String titular;

    public CuentaBancaria1410(String numCuenta, String numTdd, String clabe, String titular){
        this.numCuenta = validarNumCuenta(numCuenta);
        this.numTdd = validarNumTdd(numTdd);
        this.clabe = validarClabe(clabe);
        this.saldo = 0;//una cuenta siempre se abre en 0, hasta despues se hace el primer deposito
        this.titular = validarTitular(titular);
    }

    //validadores
    private String validarNumCuenta(String numCuenta) {
        if (!validar_long_str(numCuenta, 10, 10, true)){
            throw new IllegalArgumentException("**Numero de cuenta invalido, debe tener exactamente 10 digitos por norma del banco**");
        }
        if (!numCuenta.endsWith("7528")) {
            throw new IllegalArgumentException("**Numero de cuenta invalido, debe terminar en 7528**");
        }
        return numCuenta.replace(" ", "");
    }

    private String validarNumTdd(String numTdd) {
        if (!validar_long_str(numTdd, 16, 16, true)) {
            throw new IllegalArgumentException("**Numero de tdd invalido, debe tener exactamente 16 digitos**");
        }
        if (!numTdd.endsWith("7528")) {
            throw new IllegalArgumentException("**Numero de tdd invalido, debe terminar en 7528**");
        }
        return numTdd.replace(" ", "");
    }

    private String validarClabe(String clabe) {
        if (!validar_long_str(clabe, 18, 18, true)) {
            throw new IllegalArgumentException("**Numero de CLABE invalido, debe tener exactamente 18 digitos**");
        }
        return clabe.replace(" ", "");
    }

    private String validarTitular(String titular){
        if (titular == null) {
            throw new IllegalArgumentException("**Nombre de titular invalido**");
        }
        return normalName(titular);
    }

    //setters
    public void setNumTdd(String numTdd){
        this.numTdd = validarNumTdd(numTdd);
    }

    public void setClabe(String clabe){
        this.clabe = validarClabe(clabe);
    }
    public void setSaldo(double saldo){
        if (saldo < 0) {
            throw new IllegalArgumentException("**Intento de sobregiro**");
        }
        this.saldo = saldo;
    }
    public void setTitular(String titular){
        this.titular = validarTitular(titular);
    }

    //getters
    public String getNumCuenta(){
        return this.numCuenta;
    }
    public String getNumTdd(){
        return this.numTdd;
    }
    public String getClabe(){
        return this.clabe;
    }
    public double getSaldo(){
        return this.saldo;
    }
    public String getTitular(){
        return this.titular;
    }

    @Override
    public String toString() {
        return "CuentaBancaria1410 {" +
               "\n   Titular: " + titular +
               "\n   Numero de cuenta: " + numCuenta +
               "\n   Tarjeta de debito: " + numTdd +
               "\n   CLABE: " + clabe +
               "\n   Saldo: $" + String.format("%.2f", saldo) +
               "\n}";
    }

}
