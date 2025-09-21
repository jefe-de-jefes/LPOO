package practica4;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class AutoSegobiaTest {

    @Test
    void testAcelerarFrenar() {
        AutoSegobia auto = new AutoSegobia("Toyota", "Corolla", 2020);
        auto.encender();
        auto.acelerar(50);
        assertEquals(50, auto.velocidad, 0.01);
        auto.frenar(20);
        assertEquals(32, auto.velocidad, 0.01);
    }

    @Test
    void testClima() {
        AutoSegobia auto = new AutoSegobia("Toyota", "Corolla", 2020);
        auto.encenderClima();
        assertTrue(auto.climaEncendido);
        auto.apagarClima();
        assertFalse(auto.climaEncendido);
    }
}
