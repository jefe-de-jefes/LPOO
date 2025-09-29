package practica6;
import static lpoo.utils.Validar.*;


enum Departments {
    RH,
    SISTEMAS,
    DIRECTIVOS,
    NOMINA
}
public abstract class EmpleadoLS {
    protected String Id;
    protected String nombre;
    protected int edad;
    protected double salario;
    protected Departments departamento;
    protected String puesto;
    protected String telefono;
    protected int antiguedad; 
    protected boolean activo;

    public EmpleadoLS(String Id, String nombre, int edad, double salario,String departamento, String puesto, String telefono, int antiguedad) {
        setId(Id);
        setNombre(nombre);
        setEdad(edad);
        setSalario(salario);
        setDepartamento(departamento);
        setPuesto(puesto);
        setTelefono(telefono);
        setAntiguedad(antiguedad);
        this.activo = true;
    }

    public abstract double calcularSalario(); 

    public void mostrarInfo() {
        print("Id: " + Id);
        print("Empleado: " + nombre);
        print("Edad: " + edad);
        print("Salario: " + salario);
        print("Departamento: " + departamento);
        print("Puesto: " + puesto);
        print("Telefono: " + telefono);
        print("Antiguedad: " + antiguedad + " years");
        print("Activo: " + activo);
    }

    public void setId(String Id){
        if(validar_long_str(Id, 1, 256, true))
            this.Id = Id; 
        else{
            print("**Id no valido*");
        }
    }
    public void setNombre(String nombre) {
        this.nombre = validarName(nombre,2, 256);
    }
 
    public void setEdad(int edad){
        if (edad < 18 || edad > 65) {
            throw new IllegalArgumentException("**Edad invalida: por politicas de la empresa debe tener entre 18 y 65 years**");
        }
        this.edad = edad;
    }   
    public void setSalario(double salario) {
        if (salario <= 0) {
            throw new IllegalArgumentException("**Salario invalido: debe ser mayor a cero.**");
        }
        this.salario = salario;
    }
    public void setDepartamento(String departamento) {
        try {
            Departments resultado = Departments.valueOf(normalName(departamento)); 
            this.departamento = resultado;
        } catch (IllegalArgumentException e) {
            print("\n**Opcion invalida. Intente de nuevo.**");
        }

    }
    public abstract void setPuesto(String puesto);


    public void setTelefono(String telefono) {
        if (telefono == null || !validar_long_str(telefono, 10,10, true)) {
            throw new IllegalArgumentException("**Telefono invalido: debe contener 10 digitos.**");
        }
        this.telefono = telefono.replace(" ", "");
    }
    public void setAntiguedad(int antiguedad) {
        if (antiguedad < 0) {
            throw new IllegalArgumentException("**Antiguedad invalida: no puede ser negativa.**");
        }
        this.antiguedad = antiguedad;
    }

    public void trabajar() {
        if (activo) {
            print(nombre + " esta trabajando en " + departamento + "...");
        } else {
            print(nombre + " no esta activo actualmente.");
        }
    }

    public abstract void tomarVacaciones();


    public void aumentarSalario(double porcentaje) {
        if (porcentaje <= 0) {
            print("El porcentaje de aumento debe ser positivo.");
            return; 
        }
        salario += salario * (porcentaje / 100);
        print("Nuevo salario de " + nombre + ": " + salario);
    }

    public void cambiarDepartamento(String nuevoDepartamento) {
        setDepartamento(nuevoDepartamento);
        print(nombre + " ahora trabaja en " + departamento);
    }

    public void activarEmpleado() {
        activo = true;
        print(nombre + " ha sido activado.");
    }

    public void desactivarEmpleado() {
        activo = false;
        print(nombre + " ha sido desactivado.");
    }
    public String getNombre() {
        return this.nombre;
    }
    public String getId() {
        return this.Id;
    }
}
