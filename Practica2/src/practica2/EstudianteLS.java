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
    private String matricula;
    private int edad;
    private Carrera carrera;
    private int semestreActual;

    public EstudianteLS(String nombre, String matricula, int edad, String carrera, int semestreActual){
        setNombre(nombre);
        setMatricula(matricula);
        setEdad(edad);
        setCarrera(carrera);
        setSemestre(semestreActual);
    }

    public EstudianteLS(String nombre,String matricula){
        setMatricula(matricula);
        setNombre(nombre);
    }

    public EstudianteLS(String nombre, String matricula, int edad){
        this(nombre, matricula);
        this.edad = edad;
    }

    public void setNombre(String nombre) {
        this.nombre = validarName(nombre,2, 256);
    }
    public void setEdad(int edad){
        if (edad < 15 || edad > 45) {
            throw new IllegalArgumentException("**Edad invalida: por politicas de la universidad debe estar entre 15 y 45 years**");
        }
        this.edad = edad;
    }
    public void setMatricula(String matricula){
        if(validar_long_str(matricula, 1, 256, true))
            this.matricula = matricula; 
        else{
            print("**Matricula invalida*");
        }
    }

    public void setCarrera(String carreraStr) {
        try {
            Carrera resultado = Carrera.valueOf(carreraStr.toUpperCase()); 
            this.carrera = resultado;
        } catch (IllegalArgumentException e) {
            print("\n**Opcion invalida. Intente de nuevo.**");
        }
    }
    public void setSemestre(int semestre){
        if (semestre <= 0 || semestre > 10) {
            throw new IllegalArgumentException("\n**Solo hay 10 semestres del 1ro al 10mo**");
        }
        this.semestreActual = semestre;
    }
    public void avanzarSemestre(){
        this.semestreActual++;
    }

    public void cambiarCarrera(String nuevaCarrera){
        setCarrera(nuevaCarrera);
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

    public String getMatricula(){
        return this.matricula;
    }
    
    public String getNombre(){
        return this.nombre;
    }

}
