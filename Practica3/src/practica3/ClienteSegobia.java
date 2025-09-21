package practica3;

import static lpoo.utils.Validar.*;
enum TipoCliente{
    CLASICO,
    PREFERENTE,
    PLATINUM
}

public class ClienteSegobia{
    protected String nombre;
    protected TipoCliente tipo;
    protected int edad;
    private CuentaBancaria1410 cuenta;

    public ClienteSegobia(String nombre, String tipo, int edad) {
        setNombre(nombre);
        setTipoCliente(tipo);
        setEdad(edad);
        cuenta = null;
    }

    //setters

    public void setNombre(String nombre) {
        validarName(nombre, 2, 50);
        this.nombre = normalName(nombre);
    }
    public void setEdad(int edad){
        if (edad < 18 || edad > 75) {
            throw new IllegalArgumentException("**Edad invalida: por politicas del banco debe ser entre 18 y 75 years de edad**");
        }
        this.edad = edad;
    }

    public void setTipoCliente(String tipo) {
        try {
            this.tipo = TipoCliente.valueOf(normalName(tipo).replace(" ", ""));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("**Tipo de cliente invalido. Debe ser: CLASICO, PREFERENTE o PLATINUM.");
        }
    }

    public void setCuenta(CuentaBancaria1410 cuenta) {
        if (cuenta == null) 
            throw new IllegalArgumentException("La cuenta no puede ser nula");
        this.cuenta = cuenta;
    }

    //getters
    public String getNombre() { return nombre; }
    public TipoCliente getTipo(){return tipo;}
    public int getEdad(){ return edad; }
    public CuentaBancaria1410 getCuenta() { return cuenta; }
}
