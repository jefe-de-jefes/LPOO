package practica5;
import static lpoo.utils.Validar.*;

public class CalculadoraGeometrica7528 {

    public void procesarFigura(Figura14 figura) {
        print("\nFigura: " + figura.getNombre());
        print("Color: " + figura.getColor());
        print("Area: " + figura.calcularArea());
        print("Perimetro: " + figura.calcularPerimetro());
        figura.dibujar();
        print("\n-------------------");
    }

    public void procesarFiguras(Figura14[] figuras) {
        for (Figura14 f : figuras) {
            procesarFigura(f);
        }
    }
}
