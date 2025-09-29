package practica5;

public abstract class Figura14 implements CalculableL{
    private String nombre;
    private String color;

    public Figura14(String nombre, String color){
        this.nombre = validarNombre(nombre);

        this.color = validarColor(color);
    }
    private String validarNombre(String nombre){
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("**Nombre invalido**");
        }
        return nombre;
    }
    private String validarColor(String color){
        if (color == null || color.isBlank()) {
            throw new IllegalArgumentException("**Color invalido**");
        }
        return color;
    }

    public void setColor(String color){
        this.color = validarColor(color);
    }

    public String getNombre(){
        return this.nombre;
    }
    public String getColor(){
        return this.color;
    }
    public abstract void dibujar();

}
