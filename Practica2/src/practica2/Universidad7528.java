package practica2;
import static lpoo.utils.Validar.*;
import java.util.ArrayList;

public class Universidad7528{
    private ArrayList<EstudianteLS> estudiantes = new ArrayList<>();
    
     public void agregarEstudiante(EstudianteLS estudiante){
        estudiantes.add(estudiante);
    }
   
    public void buscarEstudiante(String dato){
        boolean find = false;
        for(EstudianteLS e: estudiantes){

            if(String.valueOf(e.getMatricula()).equals(dato)){
               e.showEstudiante();
               break;
            }
            if(e.getNombre().toLowerCase().contains(dato.toLowerCase())){
                e.showEstudiante();
                find = true;
                continue;
            }
        }

        if(!find){print("\n**Estudiante no encontrado en el arreglo...**");}
    }
    
    public void mostrarEstudiantes(){
        for(EstudianteLS e: estudiantes){
            e.showEstudiante();
        }
    }

}
