package practica3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClienteSegobiaTest {

    // Test for composition relationship
    @Test
    void testSetCuentaComposition() {
        ClienteSegobia cliente = new ClienteSegobia("Jose Perez", "CLASICO", 25);
        CuentaBancaria1410 cuenta = new CuentaBancaria1410("1234567528", "1234567812347528", "123456789012345678", "Jose Perez");
        cliente.setCuenta(cuenta);

        assertNotNull(cliente.getCuenta());
        assertEquals(cuenta, cliente.getCuenta());
    }

    @Test
    void testSetCuentaNull() {
        ClienteSegobia cliente = new ClienteSegobia("Jose Perez", "CLASICO", 25);
        assertThrows(IllegalArgumentException.class, () -> cliente.setCuenta(null));
    }

    // Test for protected members access (demonstrates protected usage)
    @Test
    void testProtectedModifierAccess() {
        // Create an instance of the subclass
        SubclaseCliente clienteSub = new SubclaseCliente("Ana Garcia", "PREFERENTE", 30);

        // This demonstrates that protected fields are accessible in subclasses
        assertEquals("Ana Garcia", clienteSub.getNombreSubclase());
        assertEquals(TipoCliente.PREFERENTE, clienteSub.getTipoSubclase());
        assertEquals(30, clienteSub.getEdadSubclase());
    }
    
    // A subclass to demonstrate protected access
    private static class SubclaseCliente extends ClienteSegobia {
        public SubclaseCliente(String nombre, String tipo, int edad) {
            super(nombre, tipo, edad);
        }

        // Methods to access protected fields
        public String getNombreSubclase() {
            return nombre; // Direct access to protected field
        }

        public TipoCliente getTipoSubclase() {
            return tipo; // Direct access to protected field
        }

        public int getEdadSubclase() {
            return edad; // Direct access to protected field
        }
    }

    @Test
    void testSetEdadValidAndInvalid() {
        ClienteSegobia cliente = new ClienteSegobia("Luis", "CLASICO", 25);
        
        // Test valid age
        assertDoesNotThrow(() -> cliente.setEdad(18));
        assertDoesNotThrow(() -> cliente.setEdad(75));

        // Test invalid ages
        assertThrows(IllegalArgumentException.class, () -> cliente.setEdad(17));
        assertThrows(IllegalArgumentException.class, () -> cliente.setEdad(76));
    }

    @Test
    void testSetTipoClienteValidAndInvalid() {
        ClienteSegobia cliente = new ClienteSegobia("Luis", "CLASICO", 25);

        // Test valid types
        assertDoesNotThrow(() -> cliente.setTipoCliente("CLASICO"));
        assertEquals(TipoCliente.CLASICO, cliente.getTipo());
        assertDoesNotThrow(() -> cliente.setTipoCliente("PREFERENTE"));
        assertEquals(TipoCliente.PREFERENTE, cliente.getTipo());
        assertDoesNotThrow(() -> cliente.setTipoCliente("PLATINUM"));
        assertEquals(TipoCliente.PLATINUM, cliente.getTipo());

        // Test invalid type
        assertThrows(IllegalArgumentException.class, () -> cliente.setTipoCliente("INVALIDO"));
    }
}
