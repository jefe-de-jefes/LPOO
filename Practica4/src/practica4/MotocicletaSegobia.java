package practica4;
import static lpoo.utils.Validar.*;

public class MotocicletaSegobia extends VehiculoL{
    protected boolean caballito;
    protected int totCaballitos;

    public MotocicletaSegobia(String marca, String modelo, int year){
        super(marca,modelo, year);
        this.caballito = false;
        this.totCaballitos = 0;
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
            if(this.velocidad + incremento*2 >= 180){
                this.velocidad = 180;
                print("**\nAcelerando...Cuidado velocidad maxima alcanzada a: 180km/h**");
                return;
            }
            this.velocidad += incremento * 2;
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

        if(caballito && velocidad - frenado*2 < 85){
            quitarCaballito();
        }
        if(velocidad < frenado*2){
            velocidad = 0;
            print("\n**Freno brusco del auto a 0 km/h**");
        } else {
            velocidad -= frenado * 2; 
            print("\nFrenando...Velocidad actual del auto: " + velocidad + " km/h");
        }

    }
    
    @Override
    public void showInfo() {
        print("\nMarca: " + marca + ", Modelo: " + modelo + ", Anio: " + year 
              + ", Encendido: " + encendido + ", Velocidad: " + velocidad + " km/h" 
              + ", Cantidad de caballitos: " + totCaballitos + "Caballito ahorita: " + caballito);
    }

    @Override
    public String tipoVehiculo(){
        return "Motocicleta";
    }

    public void makeCaballito(){
        if(!encendido || velocidad < 85){
            print("\n**Velocidad no suficiente para hacer caballito, se requiere minimo 85 km/h");
            return;
        }
        if(caballito){
            print("\n**Caballito ya en proceso, favor de quitar el caballito porque se cansa el motociclista :)..**");
            return;
        }
        this.caballito = true;
        print("\n**Realizando caballito...**");
        return;
    }

    public void quitarCaballito(){
        if(caballito){
            this.caballito = false;
            this.totCaballitos += 1;
            print("\n**Caballito quitado, total de caballitos: " + totCaballitos + "**");
            return;
        }
        print("\n**No hay caballitos en proceso**");
        return;
    }

}
