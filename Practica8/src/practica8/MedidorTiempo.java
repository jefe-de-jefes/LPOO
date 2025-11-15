package practica8;

import static lpoo.utils.Validar.*;
import java.time.Duration;

public class MedidorTiempo {

    public static void medirTiempo(long inicio) {
        try {
            long fin = System.nanoTime();
            Duration duracion = Duration.ofNanos(fin - inicio);
            System.out.printf("Tiempo de ejecucion: %.4f milisegundos\n", duracion.toNanos() / 1_000_000.0);
        } catch (Exception e){
            print("\nError al calcular tiempo de ejecucion**" + e);

        }
    }
}
