package practica9;

import  java.io.*;

public class PersonaLSerializable implements Serializable{
    private static final long serialVersionUID = 1L;

    private String nombre;
    private int matricula;
    private transient String password; 

    public PersonaLSerializable(String nombre, int matricula, String password) {
        this.nombre = nombre;
        this.matricula = matricula;
        this.password = password;
    }

    public String getNombre() { return nombre; }
    public int getMatricula() { return matricula; }
    public String getPassword() { return password; }

    @Override
    public String toString() {
        return "Persona: " + nombre + ", Matricula: " + matricula;
    }
}
