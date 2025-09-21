package practica3;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ClienteTest {
    
    @Test
    void testGettersSetters() {
        ClienteSegobia cliente = new ClienteSegobia("Luis Segobia", "CLASICO", 25);
        CuentaBancaria1410 cuenta = new CuentaBancaria1410(
            "1234567528", "1234567890127528", "123456789012345678", "Luis Segobia"
        );
        
        cliente.setCuenta(cuenta);
        
        assertEquals("Luis Segobia", cliente.getNombre());
        assertEquals(25, cliente.getEdad());
        assertEquals("CLASICO", cliente.getTipo().name());
        assertEquals(cuenta, cliente.getCuenta());
    }

    @Test
    void testValidaciones() {
        // Edad invalida
        assertThrows(IllegalArgumentException.class, () -> {
            new ClienteSegobia("Ana", "CLASICO", 10);
        });
        
        // Tipo invalido
        assertThrows(IllegalArgumentException.class, () -> {
            new ClienteSegobia("Ana", "VIP", 25);
        });
        
        // Cuenta nula
        ClienteSegobia cliente = new ClienteSegobia("Luis Segobia", "CLASICO", 25);
        assertThrows(IllegalArgumentException.class, () -> {
            cliente.setCuenta(null);
        });
    }
}
