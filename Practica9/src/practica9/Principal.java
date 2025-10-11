package practica9;

import static lpoo.utils.Validar.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.NoSuchFileException; 

public class Principal{
    
    private static final String ARCHIVO_TXT = "datos_7528.txt";
    private static final String ARCHIVO_SERIAL = "backup_1410.dat"; 
    private static final String ARCHIVO_CSV = "log_Segobia.csv";

    private static GestorArchivosSegobia7528 gestor = new GestorArchivosSegobia7528();
    
    public static void main(String[] args) {
        int opcion = -1;

        print("\n--- INICIANDO GESTOR DE ARCHIVOS I/O ---");
        pausar();

        do {
            cleaner();
            try {
                String menuPrincipal = "\n===============================================\n" +
                                       "   GESTOR ARCHIVOS  - MENU I/O\n" +
                                       "===============================================\n" +
                                       "1.  OPERACIONES DE TEXTO (.txt)\n" +
                                       "2.  OPERACIONES BINARIAS\n" +
                                       "3.  GESTION DE DIRECTORIOS\n" +
                                       "4.  SERIALIZACION DE OBJETOS (Persona)\n" +
                                       "5.  PROCESAMIENTO DE ARCHIVOS CSV\n" +
                                       "6.  CREAR BACKUP AUTOMATICO (TIMESTAMP)\n" +
                                       "0.  Salir\n" +
                                       "===============================================\n" +
                                       "Seleccione una opcion> ";
                                       
                opcion = menu(menuPrincipal, new int[] {0,1,2,3,4,5,6});

                switch (opcion) {
                    case 1: menuTexto(); break;
                    case 2: menuBinario(); break;
                    case 3: menuDirectorios(); break; 
                    case 4: serializacionMenu(); break;
                    case 5: csvMenu(); break;
                    case 6: crearBackup(); break;
                    case 0: print("\nSaliendo del Programa. Bye bye.");
                        break;
                    default:
                        print("Opcion no valida. Intente de nuevo.");
                }
            } catch (Exception e) {
                System.err.println("ERROR INESPERADO: " + e.getMessage());
                e.printStackTrace(); 
                pausar();
            }
            pausar();
        } while (opcion != 0);
    }

    //OPERACIONES DE TEXTO
    public static void menuTexto() throws IOException {
        do {
            cleaner();
            String menu = "--- OPERACIONES DE TEXTO ---\n" +
                          "1. Escribir/Anadir texto a " + ARCHIVO_TXT + "\n" +
                          "2. Leer contenido de " + ARCHIVO_TXT + "\n" +
                          "3. Vaciar archivo de texto " + ARCHIVO_TXT + "\n" +
                          "0. Volver al menu principal\n" +
                          "Seleccione una opcion> ";
            int op = menu(menu, new int[]{0, 1, 2, 3});

            if (op == 1) {
                String contenido = leer_string("Ingrese el texto a guardar: ", 1, 512, false);
                gestor.escribirTexto(ARCHIVO_TXT, contenido);
            } else if (op == 2) {
                gestor.leerTexto(ARCHIVO_TXT);
            } else if(op ==3){
                gestor.vaciarArchivo(ARCHIVO_TXT);
            } else if (op == 0) {
                return;
            }
            pausar();
        } while (true);
    }
    
    //OPERACIONES BINARIAS
    public static void menuBinario() throws IOException {
        String archivoBin = "datos_binarios.dat";
        int op;
        do {
            cleaner();
            String menu = "--- OPERACIONES BINARIAS ---\n" +
                          "1. Escribir en un archivo binario prueba\n" +
                          "2. Leer archivo binario\n" +
                          "3. Vaciar archivo binario\n" + 
                          "0. Volver al menu principal\n" +
                          "Seleccione una opcion> ";
            op = menu(menu, new int[]{0, 1, 2, 3});

            if (op == 1) {
            String mensaje = "Ingrese el texto que desea convertir a bytes y guardar (Max. 256 caracteres): ";
            String textoUsuario = leer_string(mensaje, 1, 256, false); 
            
            byte[] datos = textoUsuario.getBytes(); 
            
            gestor.escribirBinario(archivoBin, datos);
            } else if (op == 2) {
                gestor.leerBinario(archivoBin);
            }else if(op == 3){
                gestor.vaciarArchivo(archivoBin);
            } else if (op == 0) {
                return;
            }
            pausar();
        } while (true);
    }

    //GESTION DE DIRECTORIOS
    public static void menuDirectorios() {

        int op;
        do {
            cleaner();
            String menu = "--- GESTION DE DIRECTORIOS ---\n" +
                          "1. Crear directorio\n" +
                          "2. Listar contenido de directorio\n" +
                          "0. Volver al Menu Principal\n" +
                          "Seleccione una opcion> ";
            op = menu(menu, new int[]{0, 1, 2});

            if (op == 1) {
                String nuevaRuta = leer_string("Ingrese el nombre del nuevo directorio: ", 1, 50, false);
                gestor.crearDirectorio(nuevaRuta);
            } else if (op == 2) {
                String rutaListar = leer_string("Ingrese la ruta a listar", 1, 50, false);
                gestor.listarContenido(rutaListar); 
            } else if (op == 0) {
                return;
            }
            pausar();
        } while (true);
    }
    
    //SERIALIZACION DE OBJETOS
    public static void serializacionMenu() throws IOException, ClassNotFoundException {
        int op;
        do {
            cleaner();
            String menu = "--- SERIALIZACION DE OBJETOS ---\n" +
                          "1. Guardar objeto PersonaLSerializable en " + ARCHIVO_SERIAL + "\n" +
                          "2. Cargar objeto desde " + ARCHIVO_SERIAL + "\n" +
                          "0. Volver al menu principal\n" +
                          "Seleccione una opcion> ";
            op = menu(menu, new int[]{0, 1, 2});

        if (op == 1) {
            print("\n**INGRESO DE DATOS PARA SERIALIZAR**");
            
            String nombre = leer_string("Ingrese el nombre de la persona: ", 3, 50, false);
            
            String matriculaStr = leer_string("Ingrese la matricula: ", 4, 10, true); 
            int matricula = Integer.parseInt(matriculaStr); 
            
            String password = leer_string("Ingrese el password: ", 4, 20, false);
            
            PersonaLSerializable miPersona = new PersonaLSerializable(nombre, matricula, password);
            
            gestor.guardarObjeto(miPersona, ARCHIVO_SERIAL);
            
        } else if (op == 2) {
            PersonaLSerializable cargada = gestor.cargarObjeto(ARCHIVO_SERIAL);
            if (cargada != null) {
                print("\nObjeto cargado con exito: " + cargada);
                print("password: " + cargada.getPassword() + " 'Es null por seguridad'");
            }
            } else if (op == 0) {
                return;
            }
            pausar();
        } while (true);
    }
    
    //PROCESAMIENTO CSV
    public static void csvMenu() throws IOException {
        int op;
        do {
            cleaner();
            String menu = "--- PROCESAMIENTO CSV ---\n" +
                          "1. Generar y escribir en archivo de prueba(" + ARCHIVO_CSV + ")\n" +
                          "2. Leer y procesar datos del CSV\n" +
                          "0. Volver al menu principal\n" +
                          "Seleccione una opcion> ";
            op = menu(menu, new int[]{0, 1, 2});
            
            if (op == 1) {
                List<String> filasCSV = new ArrayList<>();
                
                filasCSV.add("7528,Luis Segobia,98"); 
                print("Registro inicial agregado: 7528,Luis Segobia,98");
                
                String registroUsuario = pedirRegistroEstudiante();
                filasCSV.add(registroUsuario);
                
                gestor.guardarDatosCSV(ARCHIVO_CSV, filasCSV);
            } else if (op == 2) {
                gestor.leerYProcesarCSV(ARCHIVO_CSV);
            } else if (op == 0) {
                return;
            }
            pausar();
        } while (true);
    }

    public static String pedirRegistroEstudiante() {
        print("\n--- INGRESO DE NUEVO REGISTRO ---");
        

        String matricula = leer_string("Ingrese Matricula (Ej: 7528): ", 4, 10, false);
        String nombre = leer_string("Ingrese Nombre: ", 5, 50, false);
        String calificacionStr = leer_string("Ingrese Calificacion de (Ej: 85): ", 1, 3, true);

        return matricula + "," + nombre + "," + calificacionStr;
    }
    
    //BACKUP AUTOMATICO
    public static void crearBackup() throws IOException {
        print("\n--- CREANDO BACKUP AUTOMATICO ---");
        String mensaje = "Ingrese el NOMBRE del archivo a respaldar (Ej: datos_7528.txt): ";
        
        String archivoARespaldar = leer_string(mensaje, 5, 256, false); 
        
        try {
            gestor.crearBackup(archivoARespaldar); 
        } catch (NoSuchFileException e) {
            System.err.println("ERROR: El archivo " + archivoARespaldar + " no existe. Asegurese de crearlo primero.");
        } catch (Exception e) {
            System.err.println("Error al crear backup: " + e.getMessage());
        }
    }
}
