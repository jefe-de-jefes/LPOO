package practica4;
import static lpoo.utils.Validar.*;

public class VehiculoL{
    protected String marca;
    protected String modelo;
    protected int year;
    protected double velocidad;
    protected boolean encendido;

    public VehiculoL(String marca, String modelo, int year){
        this.marca = validarMarca(marca);
        this.modelo = validarModelo(modelo);
        this.year = validarYear(year);
        this.velocidad = 0;
        this.encendido = false;
    }

    private String validarMarca(String marca){
        if (marca == null || marca.isBlank()) {
            throw new IllegalArgumentException("**Marca invalida**");
        }
        return marca;
    }
    private int validarYear(int year) {
        int currentYear = java.time.Year.now().getValue();
        if (year < 1886 || year > currentYear + 1) {
            throw new IllegalArgumentException("**Year invalido para un vehiculo**");
        }
        return year;
    }
    private String validarModelo(String modelo) {
        if (modelo == null || modelo.isBlank()) {
            throw new IllegalArgumentException("**Modelo invalido**");
        }
        return modelo;
    }

    public void encender(){
        if(this.encendido){
            print("\n**Vehiculo ya encendido anteriormente**");
            return;
        }
        this.encendido = true;
        print("**\nVehiculo encendido exitosamente**");
    }

    public void apagar(){
        if(!this.encendido){
            print("\n**Vehiculo ya pagado anteriormente**");
            return;
        }
        if(this.velocidad != 0){
            print("\n**Por seguridad primero frenar por completo el vehiculo**");
            return;
        }
        this.encendido = false;
        print("**\nVehiculo apagado exitosamente**");
    }

    public void acelerar(double incremento){
        if(incremento <= 0){
            print("\n**Incremento invalido**");
            return;
        }
        if(!this.encendido){
            print("\n**Favor de encender el vehiculo primero**");
        }else{
            this.velocidad += incremento;
            print("\nAcelerando....Velocidad actual de: " + velocidad + " km/h");
        }
    }
    public void frenar(double frenado){
        if(frenado <= 0){
            print("\n**Frenado invalido**");
            return;
        }
        if(!this.encendido){
            print("\n**Favor de encender el vehiculo primero**");
            return;
        }else{
            if(this.velocidad < frenado){
                print("\n**Cuidado freno brusco a 0 km/h**");
                this.velocidad = 0;
                return;
            }
            this.velocidad -= frenado;
            print("\nFrenando....Velocidad actual de: " + velocidad + " km/h");
            return;
        }
    }
        
    public void showInfo() {
        print("\n**Marca: " + marca + ", Modelo: " + modelo + ", Anio: " + year + "Encendido: " + encendido + ", Velocidad: " + velocidad + " km/h**");
    }

    public String tipoVehiculo() {
        return "Vehiculo genérico";
    }

}
