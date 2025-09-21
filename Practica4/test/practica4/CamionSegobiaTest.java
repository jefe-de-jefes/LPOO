package practica4;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CamionSegobiaTest {

    @Test
    void testCargaDescarga() {
        CamionSegobia camion = new CamionSegobia("Volvo", "FH", 2019);
        camion.encender();
        camion.cargar(5000);
        assertEquals(5000, camion.kgCargados);
        camion.descargar();
        assertEquals(0, camion.kgCargados);
    }
}
