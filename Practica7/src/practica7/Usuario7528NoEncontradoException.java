package practica7;

public class Usuario7528NoEncontradoException extends ExceptionSegobiaBase{
    public Usuario7528NoEncontradoException(String matricula){
        super("Usuario con matricula " + matricula + " no encontrado en la base de datos.");
    }
}
