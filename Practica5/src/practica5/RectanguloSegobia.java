package practica5;
import static lpoo.utils.Validar.*;

public class RectanguloSegobia extends Figura14{
    private double largo;
    private double ancho;

    public RectanguloSegobia(double largo, double ancho, String color){
        super("Rectangulo", color);
        this.largo = validarLado(largo);
        this.ancho = validarLado(ancho);
    }
    private double validarLado(double lado) {
        if (lado <= 0) {
            throw new IllegalArgumentException("**Lado invalido**");
        }
        return lado;
    }

    @Override
    public double calcularArea(){
        return largo * ancho;
    }

    @Override
    public double calcularPerimetro(){
        return 2 * largo + 2 * ancho;
    }

    @Override
    public void dibujar(){
        print("Rectangulo color " + getColor() + "de dimensiones: " + largo + " * " + ancho);
    }

    public void setDimensiones(double nuevaLargo, double nuevoAncho) { 
        this.largo = validarLado(nuevaLargo);
        this.ancho = validarLado(nuevoAncho);
    }
    
    public void setDimensiones(double lado) { 
        this.largo = validarLado(lado);
        this.ancho = validarLado(lado);
    }
    
    public void setDimensiones(double nuevoLargo, double nuevoAncho, String nuevoColor) { 
        try {
            this.largo = validarLado(nuevoLargo);
            this.ancho = validarLado(nuevoAncho);
            setColor(nuevoColor); 
        } catch (NumberFormatException e) {
            print("Error: Dimensiones no válidas.");
        }
    }
}
