package practica1;
import static lpoo.utils.Validar.*;
import java.time.LocalDate;

public class HolaMundoLS{
    public static void main(String[] args){
        String nombre = "Luis Fernando Segobia Torres";
        int matricula = 2177528;

        print("\nNombre: " + nombre);
        print("\nMatricula: " + matricula);
        print("\nFecha de hoy: " + LocalDate.now());
    }
}
