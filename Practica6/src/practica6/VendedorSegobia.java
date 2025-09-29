package practica6;
import static lpoo.utils.Validar.*;

enum PuestosVendedor {
    VENDEDOR,
    LIDER_DE_VENTAS
}

public class VendedorSegobia extends EmpleadoLS implements Bonificable14, Evaluable10, Promovible7528 {
    
    private double totalVentas;
    public static final double TASA_COMISION = 0.05; 

    public VendedorSegobia(String Id, String nombre, int edad, double salario, String departamento, String puesto, String telefono, int antiguedad, double totalVentas) {
        super(Id, nombre, edad, salario, departamento, puesto, telefono, antiguedad);
        this.totalVentas = totalVentas;
    }


    @Override
    public double calcularSalario() {
        return this.salario + this.calcularBono();
    }

    @Override
    public void tomarVacaciones() {
        print(getNombre() + " (Vendedor) se toma 2 semanas de vacaciones.");
    }

    @Override
    public void setPuesto(String puesto) {
        try {
            PuestosVendedor.valueOf(puesto.toUpperCase());
            this.puesto = puesto; 
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("El puesto '" + puesto + "' no es válido para un Vendedor.");
        }
    }


    @Override
    public double calcularBono() {
        return this.totalVentas * TASA_COMISION;
    }

    @Override
    public String evaluarDesempeno() {
        if (totalVentas > 100000) {
            return DESEMPENO_EXCELENTE;
        } else if (totalVentas >= 25000) {
            return DESEMPENO_BUENO;
        } else {
            return DESEMPENO_REGULAR;
        }
    }
    @Override
    public void mostrarInfo() {
        super.mostrarInfo(); 
        print("Total de Ventas: $" + this.totalVentas);
    }

    @Override
    public void promover() {
        PuestosVendedor puestoActual;
        try {
            puestoActual = PuestosVendedor.valueOf(this.puesto.toUpperCase());
        } catch (IllegalArgumentException e) {
            print("**Error: Puesto actual '" + this.puesto + "' es invalido.**");
            return;
        }

        PuestosVendedor[] todosPuestos = PuestosVendedor.values();
        int indiceActual = puestoActual.ordinal();

        if (indiceActual >= todosPuestos.length - 1) {
            print("**" + getNombre() + " ya es el jefe de jefes tiene el puesto mas alto para un vendedor: " + this.puesto + "**");
            return;
        }

        this.puesto = todosPuestos[indiceActual + 1].name();
        print("**Felicidades, " + getNombre() + " ha sido promovido al nuevo puesto de " + this.puesto + "**");
    }
}
