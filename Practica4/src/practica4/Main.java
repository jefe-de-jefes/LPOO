package practica4;

public class Main {
    public static void main(String[] args) {
        Concesionaria7528 concesionaria = new Concesionaria7528();

        VehiculoL auto = new AutoSegobia("Toyota", "Corolla", 2020);
        VehiculoL moto = new MotocicletaSegobia("Yamaha", "MT07", 2021);
        VehiculoL camion = new CamionSegobia("Volvo", "FH", 2019);

        concesionaria.agregarVehiculo(auto);
        concesionaria.agregarVehiculo(moto);
        concesionaria.agregarVehiculo(camion);

        concesionaria.mostrarTodos(); 
    }
}
