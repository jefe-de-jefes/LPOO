package practica7;

public class Matricula14InvalidaException extends ExceptionSegobiaBase{
    public Matricula14InvalidaException(String matricula){
        super("Matricula " + matricula + " invalida. Ingresa una matricula valida (ej. 2177528)");
    }
}
