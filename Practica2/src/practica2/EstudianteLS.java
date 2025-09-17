package practica2;
import static lpoo.utils.Validar.*;

enum Carrera{
    LCC,
    LSTI,
    FISICA,
    MATEMATICAS,
    LMAD
}

public class EstudianteLS{
    private String nombre;
    private int matricula;
    private int edad;
    private Carrera carrera;
    private int semestreActual;

    public EstudianteLS(String nombre, int matricula, int edad, Carrera carrera, int semestreActual){
        this.nombre = nombre;
        this.matricula = matricula;
        this.edad = edad;
        this.carrera = carrera;
        this.semestreActual = semestreActual;
    }

    public EstudianteLS(String nombre, int matricula){
        this.nombre = nombre;
        this.matricula = matricula;
    }

    public EstudianteLS(String nombre, int matricula, int edad){
        this(nombre, matricula);
        this.edad = edad;
    }

    public void avanzarSemestre(){
        this.semestreActual++;
    }

    public void cambiarCarrera(Carrera nuevaCarrera){
        this.carrera = nuevaCarrera;
    }

    public void cumplirYears(){
        this.edad++;
    }
   
    public void showEstudiante(){
        print("\n**Datos de estudiante.**");
        print("Nombre: " + this.nombre);
        print("Matricula: " + this.matricula);
        print("Edad: " + this.edad);
        print("Carrera: " + this.carrera);
        print("Semestre en curso: " + this.semestreActual);
    }

    public int getMatricula(){
        return this.matricula;
    }
    
    public String getNombre(){
        return this.nombre;
    }

}
