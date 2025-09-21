package practica4;
import static lpoo.utils.Validar.*;

public class CamionSegobia extends VehiculoL{
    public static final int CARGA_MAXIMA = 7528;
    protected boolean carga;
    protected int kgCargados;

    public CamionSegobia(String marca, String modelo, int year){
        super(marca,modelo, year);
        this.carga = false;
        this.kgCargados = 0;
    }

    @Override
    public void acelerar(double incremento) {
        if(incremento <= 0){
            print("\n**Incremento invalido**");
            return;
        }
        if(!this.encendido){
            print("\n**Favor de encender el vehiculo primero**");
        }else{
            if(this.velocidad + incremento*0.8 >= 150){
                this.velocidad = 150;
                print("**\nAcelerando...Cuidado velocidad maxima alcanzada a: 150km/h**");
                return;
            }
            this.velocidad += incremento*0.8;
            print("\nAcelerando....Velocidad actual de: " + velocidad + " km/h");
        }
    }
    
    
    @Override
    public void showInfo() {
        print("\nMarca: " + marca + ", Modelo: " + modelo + ", Anio: " + year 
              + ", Encendido: " + encendido + ", Velocidad: " + velocidad + " km/h" 
              + ", Carga total de: " + kgCargados);
    }

    @Override
    public String tipoVehiculo(){
        return "Camion";
    }

    public void cargar(int carga){
        if(carga <= 0){
            print("\n**Carga invalida**");
            return;
        }
        if(kgCargados + carga > CARGA_MAXIMA){
            print("\n**Carga no soportada**");
            return;
        }
        this.kgCargados += carga;
        this.carga = true;

    }

    public void descargar(){
        if(velocidad >0){
            print("\n**Por seguridad es necesario detener el camion para descargar**");
            return;
        }
        if(!encendido){
            print("\n**Por favor primero encienda el camion para descargar**");
            return;
        }
        if(carga){
            this.kgCargados = 0;
            this.carga = false;
            print("\n**Camion descargado**");
            return;
        }
        print("\n**Primero agregue una carga al camion**");
        return;
    }
}
