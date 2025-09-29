package practica6;
import static lpoo.utils.Validar.*;


enum PuestosGerente {
    GERENTE_DE_AREA,
    SUBDIRECTOR,
    DIRECTOR,
    DIRECTOR_GENERAL
}

public class GerenteSegobia extends EmpleadoLS implements Bonificable14, Evaluable10, Promovible7528 {

    private double bonoGestion;

    public GerenteSegobia(String Id, String nombre, int edad, double salario, String departamento, String puesto, String telefono, int antiguedad, double bonoGestion) {
        super(Id, nombre, edad, salario, departamento, puesto, telefono, antiguedad);
        setBonoGestion(bonoGestion);
    }

    public void setBonoGestion(double bonoGestion){
       if(bonoGestion <= 0 ){
         throw new IllegalArgumentException("**El bono no puede ser negativo.**");
       }
       this.bonoGestion = bonoGestion;
    }


    @Override
    public double calcularSalario() {
        return this.salario + this.bonoGestion + this.calcularBono();
    }
    
    @Override
    public void tomarVacaciones() {
        print(getNombre() + " (Gerente) se toma 4 semanas de vacaciones pagadas.");
    }

    @Override
    public void setPuesto(String puesto){
        try {
            PuestosGerente.valueOf(puesto.toUpperCase());
            this.puesto = puesto;
        } catch (IllegalArgumentException e) {
            print("\n**Opcion invalida. Intente de nuevo.**");
        }
    }
    @Override
    public void mostrarInfo() {
        super.mostrarInfo();
        print("Bono de Gestión: $" + this.bonoGestion);
    }



    @Override
    public double calcularBono() {
        return (this.salario * 0.30) + (this.antiguedad * 100);
    }

    @Override
    public String evaluarDesempeno() {
        return this.antiguedad > 5 ? Evaluable10.DESEMPENO_EXCELENTE : Evaluable10.DESEMPENO_BUENO;
    }

    @Override
    public void promover() {
        PuestosGerente puestoActual;

        try{
            puestoActual = PuestosGerente.valueOf(this.puesto.toUpperCase());
        }catch (IllegalArgumentException e){
            print("**Puesto actual invalido**");
            return;
        }

        PuestosGerente[] todosPuestos = PuestosGerente.values();

        int indice = puestoActual.ordinal();

        if(indice >= todosPuestos.length -1){
            print("**Usted ya es el jefe de jefes de los gerentes, tiene el puesto de " + this.puesto + "**.");
            return;
        }
        this.puesto = todosPuestos[indice+1].name();
        print("**Felicidades, " + getNombre() + " ha sido promovido al nuevo puesto de " + this.puesto);
    }
}
