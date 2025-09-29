package practica5;
import static lpoo.utils.Validar.*;

public class CirculoSegobia extends Figura14{
    private double radio;

    public CirculoSegobia(double radio, String color){
        super("Circulo", color);
        this.radio = validarRadio(radio);
    }
    private double validarRadio(double radio) {
        if (radio <= 0) {
            throw new IllegalArgumentException("**Radio invalido**");
        }
        return radio;
    }

    @Override
    public double calcularArea(){
        return Math.PI * radio * radio;
    }

    @Override
    public double calcularPerimetro(){
        return 2 * Math.PI * radio;
    }

    @Override
    public void dibujar(){
        print("Circulo color " + getColor() + " de radio: " + radio);
    }

    public void setDimensiones(double radio) {
        this.radio = validarRadio(radio);

    }

    public double getRadio(){
        return radio;
    }

    public void setDimensiones(double radio, String color) {
        this.radio = validarRadio(radio);
        setColor(color);
    }

    public void setDimensiones(double valor, String nuevoColor, boolean isDiametro) {
        if(isDiametro){
            this.radio = validarRadio(valor/2);
        }else{
            this.radio = validarRadio(valor);
        }
        setColor(nuevoColor); 
    }

}
