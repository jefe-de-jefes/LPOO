package practica4;
import static lpoo.utils.Validar.*;

public class AutoSegobia extends VehiculoL{
    protected boolean climaEncendido;

    public AutoSegobia(String marca, String modelo, int year){
        super(marca,modelo, year);
        this.climaEncendido = false;
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
            if(this.velocidad + incremento >= 200){
                this.velocidad = 200;
                print("**\nAcelerando...Cuidado velocidad maxima alcanzada a: 200km/h**");
                return;
            }
            this.velocidad += incremento;
            print("\nAcelerando....Velocidad actual de: " + velocidad + " km/h");
        }
    }
    @Override
    public void frenar(double frenado) {
        if(frenado <= 0){
            print("\n**Frenado invalido**");
            return;
        }
        if(!encendido){
            print("\n**Favor de encender el auto primero**");
            return;
        }
        if(velocidad < frenado){
            velocidad = 0;
            print("\n**Freno brusco del auto a 0 km/h**");
        } else {
            velocidad -= frenado * 0.9; 
            print("\nFrenando...Velocidad actual del auto: " + velocidad + " km/h");
        }
    }
    
    @Override
    public void showInfo() {
        print("\nMarca: " + marca + ", Modelo: " + modelo + ", Anio: " + year 
              + ", Encendido: " + encendido + ", Velocidad: " + velocidad + " km/h" 
              + ", Clima encendido: " + climaEncendido);
    }

    @Override
    public String tipoVehiculo(){
        return "Automovil";
    }

    public void encenderClima(){
        if(climaEncendido){
            return;
        }
        this.climaEncendido = true;
        print("\n**Prendiendo clima....Clima encendido**");
        return;
    }

    public void apagarClima(){
        if(!climaEncendido){
            print("\n**Clima ya apagado anteriormente**");
            return;
        }
        this.climaEncendido = false;
        print("\n**Apagando clima....Clima apagado**");
        return;
    }

}
