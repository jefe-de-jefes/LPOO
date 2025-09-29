package practica6;
import static lpoo.utils.Validar.*;

import java.util.ArrayList;
import java.util.Iterator;

public class EmpresaTIL7528 {
    
    private String nombreEmpresa;
    private ArrayList<EmpleadoLS> listaEmpleados;

    public EmpresaTIL7528(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
        this.listaEmpleados = new ArrayList<>();
    }


    public void contratarEmpleado(EmpleadoLS nuevoEmpleado) {
        if (nuevoEmpleado != null) {
            this.listaEmpleados.add(nuevoEmpleado);
            print("¡Bienvenido a " + this.nombreEmpresa + ", " + nuevoEmpleado.nombre + "!");
        } else {
            print("Error: No se puede contratar a un empleado nulo.");
        }
    }


    public void despedirEmpleado(String Id) {
        EmpleadoLS empleadoEncontrado = null;

        for (EmpleadoLS empleado : this.listaEmpleados) {
            if (empleado.getId().equalsIgnoreCase(Id)) {
                empleadoEncontrado = empleado;
                break;
            }
        }

        if (empleadoEncontrado != null) {
            String nombreDespedido = empleadoEncontrado.getNombre();
            this.listaEmpleados.remove(empleadoEncontrado);
            
            print("El empleado con Id '" + Id + "' (" + nombreDespedido + ") ha sido despedido.");
        } else {
            print("No se encontro a ningun empleado con el Id " + Id);
        }
    }


    public void mostrarTodosLosEmpleados() {
        print("\n--- Lista de Empleados en " + this.nombreEmpresa + " ---");
        if (listaEmpleados.isEmpty()) {
            print("No hay empleados contratados actualmente.");
            return;
        }
        for (EmpleadoLS empleado : this.listaEmpleados) {
            empleado.mostrarInfo(); 
            print("--------------------");
        }
    }


    public void ejecutarPromociones() {
        print("\n--- Ejecutando promociones automaticas ---");
        for (EmpleadoLS empleado : this.listaEmpleados) {
            if (empleado instanceof Promovible7528) {
                ((Promovible7528) empleado).promover();
            } else {
                print(empleado.getNombre() + " no tiene un plan de carrera para ser promovido.");
            }
        }
    }


    public void calcularNominaTotal() {
        print("\n--- Calculando Nomina Total del Mes ---");
        double nominaTotal = 0.0;
        for (EmpleadoLS empleado : this.listaEmpleados) {
            nominaTotal += empleado.calcularSalario();
        }
        print("El pago total de la nomina para " + listaEmpleados.size() + " empleados es: $" + nominaTotal);
    }

    public String getNombreEmpresa(){
        return this.nombreEmpresa;
    }
}
