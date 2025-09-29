package practica6;
import static lpoo.utils.Validar.*;
import java.util.Arrays;
import java.util.ArrayList;

enum PuestosDesarrollador {
    JUNIOR,
    SEMI_SENIOR,
    SENIOR,
    LIDER_TECNICO,
    ARQUITECTO
}
public class DesarrolladorSegobia extends EmpleadoLS implements Bonificable14, Evaluable10, Promovible7528{
    private ArrayList<String> listaLenguajes = new ArrayList<>();

    public DesarrolladorSegobia(String Id, String nombre, int edad, double salario, String departamento, String puesto, String telefono, int antiguedad, String[] lenguajes) {
        super(Id, nombre, edad, salario, departamento, puesto, telefono, antiguedad);
        setListaLenguajes(lenguajes);
    }

    public void setListaLenguajes(String[] lenguajes) {
        if (lenguajes == null || lenguajes.length == 0) {
            throw new IllegalArgumentException("**El desarrollador debe conocer al menos un lenguaje de programacion, no esta en FAMUS.**");
        }
        
        this.listaLenguajes = new ArrayList<>(Arrays.asList(lenguajes));
    }

    @Override
    public double calcularSalario() {
        return this.salario + this.calcularBono();
    }

    @Override
    public void tomarVacaciones() {
        print(getNombre() + " (Desarrollador) se toma 3 semanas de vacaciones.");
    }

    @Override
    public void setPuesto(String puesto){
        try {
            PuestosDesarrollador.valueOf(puesto.toUpperCase());
            this.puesto = puesto;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("El puesto '" + puesto + "' no es valido para un Desarrollador.");
        }
    }

    @Override
    public void mostrarInfo(){
        super.mostrarInfo();
        print("Lenguajes de progra: " + this.listaLenguajes );
    }


    @Override
    public double calcularBono() {
        return salario * 0.15;
    }

    @Override
    public String evaluarDesempeno() {
        if (salario > 20000) {
            return DESEMPENO_EXCELENTE;
        } else if (salario >= 10000) {
            return DESEMPENO_BUENO;
        } else {
            return DESEMPENO_REGULAR;
        }
    }

    @Override
    public void promover() {
        PuestosDesarrollador puestoActual;

        try{
            puestoActual = PuestosDesarrollador.valueOf(this.puesto.toUpperCase());
        }catch (IllegalArgumentException e){
            print("**Puesto actual invalido**");
            return;
        }

        PuestosDesarrollador[] todosPuestos = PuestosDesarrollador.values();

        int indice = puestoActual.ordinal();

        if(indice >= todosPuestos.length -1){
            print("**Usted ya es el jefe de jefes de los desarrolladores, tiene el puesto de " + this.puesto + "**.");
            return;
        }
        this.puesto = todosPuestos[indice+1].name();
        print("**Felicidades, " + getNombre() + " ha sido promovido al nuevo puesto de " + this.puesto);
    }
}
