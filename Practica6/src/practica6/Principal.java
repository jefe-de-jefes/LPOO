package practica6;
import static lpoo.utils.Validar.*;

public class Principal {

    public static void main(String[] args) {
        cleaner();
        print("====== GESTION DE LA EMPRESA ======");

        EmpresaTIL7528 ganaderos = new EmpresaTIL7528("***LOS GANADEROS***");
        print("\nSe ha creado la empresa: " + ganaderos.getNombreEmpresa());

        print("\n====== CONTRATANDO EMPLEADOS ======");
        try {
            String[] langsDev1 = {"Java", "Python", "SQL"};
            DesarrolladorSegobia dev1 = new DesarrolladorSegobia("0001", "Ana Torres", 28, 25000, "SISTEMAS", "JUNIOR", "8112345678", 3, langsDev1);

            VendedorSegobia vendedor1 = new VendedorSegobia("0002", "Carlos Ruiz", 35, 12000, "NOMINA", "VENDEDOR", "8187654321", 6, 1200000);
            
            GerenteSegobia gerente1 = new GerenteSegobia("0003", "Sofia Herrera", 42, 50000, "DIRECTIVOS ", "GERENTE_DE_AREA", "8111223344", 10, 15000);

            ganaderos.contratarEmpleado(dev1);
            ganaderos.contratarEmpleado(vendedor1);
            ganaderos.contratarEmpleado(gerente1);

        } catch (IllegalArgumentException e) {
            print("Error al crear un empleado: " + e.getMessage());
        }

        ganaderos.mostrarTodosLosEmpleados();

        print("\n====== DEMOSTRACIÓN DE VALIDACIÓN ======");
        try {
            DesarrolladorSegobia devInvalido = new DesarrolladorSegobia("1232", "Pedro Falla", 22, 15000, "RH", "JUNIOR", "8144556677", 1,new String[0]);
            ganaderos.contratarEmpleado(devInvalido);
        } catch (IllegalArgumentException e) {
            print("Intento de contratacion fallida. Razon: " + e.getMessage());
        }

        print("\n====== EJECUTANDO PROMOCIONES ANUALES ======");
        ganaderos.ejecutarPromociones();
        
        ganaderos.mostrarTodosLosEmpleados();

        print("\n====== DESPIDIENDO A UN EMPLEADO ======");
        ganaderos.despedirEmpleado("0001");

        ganaderos.mostrarTodosLosEmpleados();

        print("\n====== 6. CALCULANDO NOMINA MENSUAL ======");
        ganaderos.calcularNominaTotal();

        print("\n====== BYE BYE ======");
    }
    
}
