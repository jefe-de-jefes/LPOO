package practica11;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Estudiantes{
    private final StringProperty matricula;
    private final StringProperty nombre;
    private final StringProperty email;
    private final StringProperty edad; 

    public Estudiantes(String matricula, String nombre, String email, String edad) {
        this.matricula = new SimpleStringProperty(matricula);
        this.nombre = new SimpleStringProperty(nombre);
        this.email = new SimpleStringProperty(email);
        this.edad = new SimpleStringProperty(edad);
    }
    
    public StringProperty matriculaProperty() { return matricula; }
    public StringProperty nombreProperty() { return nombre; }
    public StringProperty emailProperty() { return email; }
    public StringProperty edadProperty() { return edad; }
    
    public void setNombre(String nombre) {this.nombre.set(nombre);}
    public void setEmail(String email) {this.email.set(email);}
    public void setEdad(String edad) {this.edad.set(edad);}
    
    public String getMatricula() { return matricula.get(); }
    public String getNombre() { return nombre.get(); }
    public String getEmail() { return email.get(); }
    public String getEdad() { return edad.get(); }
}
