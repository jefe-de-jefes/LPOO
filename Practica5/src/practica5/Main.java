package practica5;
import static lpoo.utils.Validar.*;

public class Main {
    public static void main(String[] args) {
        Figura14[] figuras = {
            new CirculoSegobia(5, "Verde"),
            new RectanguloSegobia(4, 6, "Rojo"),
            new TrianguloSegobia(3, 4, 5, "Azul")
        };

        CalculadoraGeometrica7528 calc = new CalculadoraGeometrica7528();
        calc.procesarFiguras(figuras); 

        
        Figura14 f = new CirculoSegobia(10, "Amarillo");

        if (f instanceof CirculoSegobia) {
            CirculoSegobia c = (CirculoSegobia) f;
            print("\nSe detectó un círculo con radio: " + c.getRadio());
            f.dibujar();
        }

    }
}
