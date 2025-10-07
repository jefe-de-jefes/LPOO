package practica7;
import static lpoo.utils.Validar.*;

import java.io.FileWriter;
import java.io.IOException;

public class FileResource {


    public static void registrarRecurso(String archivo, String mensaje) {
        
        try (FileWriter writer = new FileWriter(archivo, true)) {
            writer.write(mensaje + "\n");
            print("\n-> Registro escrito con exito en: " + archivo);
        } catch (IOException e) {
            System.err.println("Error al acceder al recurso de archivo: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        registrarRecurso("registro_recursos.txt", "Prueba de inicio de cierre automático.");
        registrarRecurso("registro_recursos.txt", "Fin de la prueba.");
    }
}
