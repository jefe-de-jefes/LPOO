package practica4;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MotocicletaSegobiaTest {

    @Test
    void testCaballito() {
        MotocicletaSegobia moto = new MotocicletaSegobia("Yamaha", "MT07", 2021);
        moto.encender();
        moto.acelerar(90);
        moto.makeCaballito();
        assertTrue(moto.caballito);
        moto.quitarCaballito();
        assertFalse(moto.caballito);
        assertEquals(1, moto.totCaballitos);
    }
}
