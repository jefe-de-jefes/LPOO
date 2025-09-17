package lpoo.utils;
import java.util.Scanner;

public class Validar{
        
    private static boolean validar_entero(String numero){
        try{
            Integer.parseInt(numero);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }

    private static boolean validar_flotante(String numero){
        try{
            Double.parseDouble(numero);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }

    public static void print(String mensaje){
        System.out.println(mensaje);
    }

    public static String leer_string(String mensaje){
        Scanner sc = new Scanner(System.in);
        String entrada;

        while(true){
            print(mensaje);
            entrada = sc.nextLine().trim();

            if(entrada.isEmpty()){
                print("\n**La entrada no puede estar vacia. Intente nuevamente...**");
                continue;
            }
            break;
        }
        return entrada;
    }

    public static int leer_entero(String mensaje, int min, int max){
        String entrada;
        int salida;

        while(true){
            entrada = leer_string(mensaje);

            if(!validar_entero(entrada)){
                print("\n**Ingrese un numero entero. Intente nuevamente...**");
                continue;
            }

            salida = Integer.parseInt(entrada);

            if(salida < min || salida > max){
                print("\n**Ingrese un numero dentro dl rango: " + min + " <= numero <= " + max);
                continue;
            }

            return salida;
        }
    }

    public static double leer_floante(String mensaje, double min, double max){
        String entrada;
        double salida;

        while(true){
            entrada = leer_string(mensaje);

            if(!validar_flotante(entrada)){
                print("\n**Ingrese un numero flotante. Intente nuevamente...**");
                continue;
            }

            salida = Double.parseDouble(entrada);

            if(salida < min || salida > max){
                print("\n**Ingrese un numero dentro del rango: " + min + " <= numero <= " + max+ "**");
                continue;
            }

            return salida;
        } 
    
    }

    public static int menu(String mensaje, int[] opciones){
        int opcion;
        while(true){
            opcion = leer_entero(mensaje, Integer.MIN_VALUE, Integer.MAX_VALUE);
            for(int num : opciones){
                if(opcion == num) return opcion;
            }
            print("\n**Opción invalida. Intente nuevamente.**");
        }
    }

    public static boolean validar_sn(String mensaje){
        String entrada;
        while(true){
            entrada = leer_string(mensaje + "(s/n): ").toLowerCase();
            if(entrada.equals("s")){return true;}
            else if(entrada.equals("n")){return false;}
            else{print("\n**Ingresa (s/n) para confirmar o negar please...**");continue;}
            
        }
    }

    
    public static <T extends Enum<T>> T leer_enum(String mensaje, Class<T> enumClass) {
        T resultado = null;

        while (resultado == null) {
            print(mensaje);

            for (T opcion : enumClass.getEnumConstants()) {
                print("\n- " + opcion);
            }

            String entrada = leer_string("\nIngresa tu elección: ").toUpperCase();

            try {
                resultado = Enum.valueOf(enumClass, entrada);
            } catch (IllegalArgumentException e) {
                print("\n**Opción invalida. Intente de nuevo.**");
            }
        }

        return resultado;
    }

    public static void cleaner() {
        try {
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("windows")) {
                // Ejecuta cls en Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Linux o macOS
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // En caso de error, solo imprime muchas líneas
            for (int i = 0; i < 50; i++) System.out.println();
        }
    }

    public static void pausar() {
        System.out.print("\nPresiona Enter para continuar...");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();  // Espera a que el usuario presione Enter
    }

}
