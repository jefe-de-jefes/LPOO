package lpoo.utils;
import java.util.Scanner;
import java.text.Normalizer;

public class Validar{
        

public static String normalName(String texto) {
    if (texto == null) return null;

    String temp = Normalizer.normalize(texto, Normalizer.Form.NFD);
    temp = temp.replaceAll("\\p{M}", ""); 

    temp = temp.toUpperCase();

    return temp.trim();
}
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

    public static boolean validar_long_str(String texto, int min_long, int max_long, boolean es_numerico){//si es numerico se selecciona true para eliminar espacios dentro del numero
        if(texto == null) return false;
        
        if(es_numerico){
            texto = texto.replace(" ", "");
            if(!texto.matches("\\d+")) return false;
        }

        return texto.length() >= min_long && texto.length() <= max_long;
    }

    public static String leer_string(String mensaje, int min_caracteres, int max_caracteres, boolean es_numerico){
        Scanner sc = new Scanner(System.in);
        String entrada;

        while(true){
            print(mensaje);
            entrada = sc.nextLine().trim();

            if(entrada.isEmpty()){
                print("\n**La entrada no puede estar vacia. Intente nuevamente...**");
                continue;
            }


            if(!validar_long_str(entrada, min_caracteres, max_caracteres, es_numerico)){
                print("\n**Porfavor introduzca una entrada con los caracteres validos entre " + min_caracteres + " y " + max_caracteres + (es_numerico ? " y solo numeros": "") + "**");
                continue;
            }
            
            break;
        }
        if(es_numerico) return entrada.replace(" ", "");
        return entrada;
    }

    public static String validarName(String nombre, int minLong, int maxLong) {
        if (nombre == null || nombre.trim().isBlank()) {
            throw new IllegalArgumentException("**La entrada no puede ser nula ni vacia**");
        }

        String temp = nombre.trim();

        if (temp.length() < minLong || temp.length() > maxLong) {
            throw new IllegalArgumentException("**El nombre debe tener entre " + minLong + " y " + maxLong + " caracteres**");
        }

        if (!temp.matches("[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]+")) {
            throw new IllegalArgumentException("**El nombre contiene caracteres invalidos**");
        }
        return nombre;
    }

    public static int leer_entero(String mensaje, int min, int max){
        String entrada;
        int salida;

        while(true){
            entrada = leer_string(mensaje, 1, 10, true);

            if(!validar_entero(entrada)){
                print("\n**Ingrese un numero entero. Intente nuevamente...**");
                continue;
            }

            salida = Integer.parseInt(entrada);

            if(salida < min || salida > max){
                print("\n**Ingrese un numero dentro del rango: " + min + " <= numero <= " + max);
                continue;
            }

            return salida;
        }
    }

    public static double leer_flotante(String mensaje, double min, double max){
        String entrada;
        double salida;

        while(true){
            entrada = leer_string(mensaje,1 , 40, true);

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
            entrada = leer_string(mensaje + "(s/n): ", 1, 2, false).toLowerCase();
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

            String entrada = leer_string("\nIngresa tu elección: ", 1, 256, false).toUpperCase();

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
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) System.out.println();
        }
    }

    public static void pausar() {
        System.out.print("\nPresiona Enter para continuar...");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();  
    }

}
