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
                print("**La entrada no puede estar vacia. Intente nuevamente...**");
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
                print("**Ingrese un numero entero. Intente nuevamente...**");
                continue;
            }

            salida = Integer.parseInt(entrada);

            if(salida < min || salida > max){
                print("**Ingrese un numero dentro dl rango: " + min + " <= numero <= " + max);
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
                print("**Ingrese un numero flotante. Intente nuevamente...**");
                continue;
            }

            salida = Double.parseDouble(entrada);

            if(salida < min || salida > max){
                print("**Ingrese un numero dentro del rango: " + min + " <= numero <= " + max+ "**");
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
            print("**Opción invalida. Intente nuevamente.**");
        }
    }


}
