package practica3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CuentaBancaria1410Test {

    // Helper method to create a valid bank account object
    private CuentaBancaria1410 createValidAccount() {
        return new CuentaBancaria1410("1234567528", "1234567812347528", "123456789012345678", "Juan Perez");
    }

    @Test
    void testConstructorValidData() {
        assertDoesNotThrow(() -> createValidAccount());
    }

    @Test
    void testConstructorInvalidNumCuenta() {
        // Must have exactly 10 digits
        assertThrows(IllegalArgumentException.class, () -> new CuentaBancaria1410("12345", "...", "...", "..."));
        // Must end with 7528
        assertThrows(IllegalArgumentException.class, () -> new CuentaBancaria1410("1234567890", "...", "...", "..."));
    }

    @Test
    void testConstructorInvalidNumTdd() {
        // Must have exactly 16 digits
        assertThrows(IllegalArgumentException.class, () -> new CuentaBancaria1410("1234567528", "123", "...", "..."));
        // Must end with 7528
        assertThrows(IllegalArgumentException.class, () -> new CuentaBancaria1410("1234567528", "1234567812341234", "...", "..."));
    }

    @Test
    void testConstructorInvalidClabe() {
        // Must have exactly 18 digits
        assertThrows(IllegalArgumentException.class, () -> new CuentaBancaria1410("1234567528", "1234567812347528", "123", "..."));
    }

    @Test
    void testSettersValidData() {
        CuentaBancaria1410 cuenta = createValidAccount();
        assertDoesNotThrow(() -> cuenta.setNumTdd("9876543210987528"));
        assertEquals("9876543210987528", cuenta.getNumTdd());

        assertDoesNotThrow(() -> cuenta.setClabe("112233445566778899"));
        assertEquals("112233445566778899", cuenta.getClabe());

        assertDoesNotThrow(() -> cuenta.setTitular("Maria Rodriguez"));
        assertEquals("Maria Rodriguez", cuenta.getTitular());

        assertDoesNotThrow(() -> cuenta.setSaldo(1500.50));
        assertEquals(1500.50, cuenta.getSaldo(), 0.001);
    }

    @Test
    void testSetSaldoNegativeValue() {
        CuentaBancaria1410 cuenta = createValidAccount();
        assertThrows(IllegalArgumentException.class, () -> cuenta.setSaldo(-100));
    }

    @Test
    void testToStringMethod() {
        CuentaBancaria1410 cuenta = createValidAccount();
        String expectedString = "CuentaBancaria1410 {\n" +
                               "   Titular: Juan Perez\n" +
                               "   Numero de cuenta: 1234567528\n" +
                               "   Tarjeta de debito: 1234567812347528\n" +
                               "   CLABE: 123456789012345678\n" +
                               "   Saldo: $0.00\n" +
                               "}";
        assertEquals(expectedString, cuenta.toString());
    }
}
