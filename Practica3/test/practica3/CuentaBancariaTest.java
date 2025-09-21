package practica3;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CuentaBancariaTest {
    
    @Test
    void testGettersSetters() {
        CuentaBancaria1410 cuenta = new CuentaBancaria1410(
            "1234567528", "1234567890127528", "123456789012345678", "Luis Segovia"
        );
        
        // Set y Get titular
        cuenta.setTitular("Juan Perez");
        assertEquals("Juan Perez", cuenta.getTitular());
        
        // Set y Get saldo
        cuenta.setSaldo(500.0);
        assertEquals(500.0, cuenta.getSaldo());
        
        // Set y Get TDD
        cuenta.setNumTdd("1111222233337528");
        assertEquals("1111222233337528", cuenta.getNumTdd());
        
        // Set y Get CLABE
        cuenta.setClabe("123456789012345678");
        assertEquals("123456789012345678", cuenta.getClabe());
    }
    
    @Test
    void testValidaciones() {
        // Numero de cuenta invalido
        assertThrows(IllegalArgumentException.class, () -> {
            new CuentaBancaria1410("123", "1234567890127528", "123456789012345678", "Luis");
        });

        // Sobregiro
        CuentaBancaria1410 cuenta = new CuentaBancaria1410(
            "1234567528", "1234567890127528", "123456789012345678", "Luis Segovia"
        );
        assertThrows(IllegalArgumentException.class, () -> {
            cuenta.setSaldo(-100);
        });
    }
}
