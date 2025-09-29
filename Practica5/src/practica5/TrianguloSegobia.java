package practica5;
import static lpoo.utils.Validar.*;

public class TrianguloSegobia extends Figura14 {
    private double ladoA;
    private double ladoB;
    private double ladoC;

    public TrianguloSegobia(double ladoA, double ladoB, double ladoC, String color) {
        super("Triangulo", color);
        this.ladoA = validarLado(ladoA);
        this.ladoB = validarLado(ladoB);
        this.ladoC = validarLado(ladoC);
    }

    private double validarLado(double lado) {
        if (lado <= 0) {
            throw new IllegalArgumentException("**Lado invalido**");
        }
        return lado;
    }

    @Override
    public double calcularArea() {
        double s = (ladoA + ladoB + ladoC) / 2.0;
        return Math.sqrt(s * (s - ladoA) * (s - ladoB) * (s - ladoC));
    }

    @Override
    public double calcularPerimetro() {
        return ladoA + ladoB + ladoC;
    }

    @Override
    public void dibujar() {
        print("Triangulo color " + getColor() + 
              " con lados: " + ladoA + ", " + ladoB + ", " + ladoC);
    }


    public void setDimensiones(double a, double b, double c) {
        this.ladoA = validarLado(a);
        this.ladoB = validarLado(b);
        this.ladoC = validarLado(c);
    }

    public void setDimensiones(double lado) {
        this.ladoA = this.ladoB = this.ladoC = validarLado(lado);
    }

    public void setDimensiones(double a, double b, double c, String nuevoColor) {
        this.ladoA = validarLado(a);
        this.ladoB = validarLado(b);
        this.ladoC = validarLado(c);
        setColor(nuevoColor); 
    }
}
