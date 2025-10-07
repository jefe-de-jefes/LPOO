package practica7;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SistemaBancoLSTest {

    private final SistemaBancoLS banco = new SistemaBancoLS();
    

    @Test
    void testA_SaldoInsuficienteLanzaExcepcion() {
        assertThrows(Saldo10InsuficienteException.class, () -> {
            banco.retiro("6543210", 1000.00); 
        }, "El retiro de 1000.00 de una cuenta de 500.00 debe lanzar Saldo10InsuficienteException.");
    }

    @Test
    void testB_MatriculaInvalidaLanzaExcepcion() {
        assertThrows(Matricula14InvalidaException.class, () -> {
            banco.retiro("1B567", 100.00); 
        }, "Una matricula alfanumerica debe lanzar Matricula14InvalidaException.");
    }

    @Test
    void testC_UsuarioNoEncontradoLanzaExcepcion() {
        assertThrows(Usuario7528NoEncontradoException.class, () -> {
            banco.retiro("9999999", 100.00);
        }, "Una matricula que no existe debe lanzar Usuario7528NoEncontradoException.");
    }
    
    @Test
    void testD_OperacionExitosa() {
        assertDoesNotThrow(() -> {
            banco.retiro("1234567", 500.00);
        }, "Un retiro valido no debe lanzar ninguna excepcion.");
    }
}
