package practica4;
import java.util.ArrayList;

public class Concesionaria7528{
    private ArrayList<VehiculoL> vehiculos;

    public Concesionaria7528() {
        vehiculos = new ArrayList<>();
    }

    public void agregarVehiculo(VehiculoL v) {
        vehiculos.add(v);
    }

    public void mostrarTodos() {
        for(VehiculoL v : vehiculos){
            v.showInfo();
        }
    }
}
