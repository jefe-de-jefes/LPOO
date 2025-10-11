package practica9;

import static lpoo.utils.Validar.*;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; 


public class GestorArchivosSegobia7528{

    //archivos de texto
    public void escribirTexto(String nombreArchivo, String contenido) throws IOException {
        //validaciones del nombre de archivo
        if (nombreArchivo.contains(".") && !nombreArchivo.endsWith(".txt")) {
            throw new IllegalArgumentException("Nombre de archivo invalido, debe terminar en .txt o solo el nombre del archivo");
        }
        if (!nombreArchivo.contains(".")) {
            nombreArchivo += ".txt";
        }

        try (FileWriter fw = new FileWriter(nombreArchivo, true); 
            BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(contenido);
            bw.newLine();
            print("\n**Archivo de texto con escritura creado.**");
        }
    }

    public void leerTexto(String nombreArchivo) throws IOException {
        if (nombreArchivo.contains(".") && !nombreArchivo.endsWith(".txt")) {
            throw new IllegalArgumentException("Nombre de archivo invalido, debe terminar en .txt o solo el nombre del archivo");
        }
        if (!nombreArchivo.contains(".")) {
            nombreArchivo += ".txt";
        }

        print("\n**Contenido de " + nombreArchivo + "**");
        try (FileReader fr = new FileReader(nombreArchivo);
            BufferedReader br = new BufferedReader(fr)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                print("\n"+linea);
            }
        }
    }
    
    //archivos binario
    public void escribirBinario(String nombreArchivo, byte[] datos) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(nombreArchivo, true)) { 
            fos.write(datos);
            print("\nArchivo binario escrito/anadido: " + nombreArchivo);
        }
    }

    public void leerBinario(String nombreArchivo) throws IOException {
        print("\n**Leyendo binario: " + nombreArchivo + "**");
        
        try (FileInputStream fis = new FileInputStream(nombreArchivo)) {
            
            byte[] buffer = fis.readAllBytes();
            
            print("Tamano: " + buffer.length + " bytes");
            
            String contenidoLeido = new String(buffer);
            print("Contenido: " + contenidoLeido);
            
            StringBuilder bytesCrudos = new StringBuilder();
            for (byte b : buffer) {
                bytesCrudos.append(b).append(" ");
            }
            print("Bytes en bin: " + bytesCrudos.toString().trim());

        } catch (FileNotFoundException e) {
            System.err.println("ERROR: El archivo binario '" + nombreArchivo + "' no fue encontrado.");
            throw e;
        }
    }
    

    //vaciar archivo
    public void vaciarArchivo(String nombreArchivo) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(nombreArchivo)) {
            print("\n**[VACIO] Contenido de '" + nombreArchivo + "' ha sido eliminado.**");
        } catch (FileNotFoundException e) {
            throw e;
        }
    }

    //directorio
    public void crearDirectorio(String rutaDirectorio) {
        File directorio = new File(rutaDirectorio);
        if (!directorio.exists()) {
            if (directorio.mkdirs()) { 
                print("\nDirectorio creado: " + rutaDirectorio);
            } else {
                System.err.println("Error al crear el directorio.");
            }
        } else {
            print("\nEl directorio ya existe.");
        }
    }

    public void listarContenido(String rutaDirectorio) {
        File directorio = new File(rutaDirectorio);
        if (directorio.exists() && directorio.isDirectory()) {
            print("\n**Contenido de " + rutaDirectorio + "**");
            String[] contenido = directorio.list();
            for (String item : contenido) {
                print("\n"+item);
            }
        } else {
            System.err.println("La ruta no es un directorio valido.");
        }
    }
    
    //serializacion
    public void guardarObjeto(PersonaLSerializable obj, String nombreArchivo) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(nombreArchivo);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(obj);
            print("\n**[SERIALIZACION] Objeto guardado en: " + nombreArchivo + "**");
        }
    }

    public PersonaLSerializable cargarObjeto(String nombreArchivo) throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(nombreArchivo);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            PersonaLSerializable persona = (PersonaLSerializable) ois.readObject();
            print("\n**[SERIALIZACION] Ulimo objeto cargado de: " + nombreArchivo + "**");
            return persona;
        }
    }

    //manejo csv
    public void guardarDatosCSV(String nombreArchivo, List<String> filasDatos) throws IOException {
        try (FileWriter fw = new FileWriter(nombreArchivo, false); 
             BufferedWriter bw = new BufferedWriter(fw)) {
            
            bw.write("Matricula,NombreCompleto,Calificacion"); 
            bw.newLine();
            
            for (String fila : filasDatos) {
                bw.write(fila);
                bw.newLine();
            }
            print("\n**[CSV] log_[apellido].csv generado/actualizado: " + nombreArchivo + "**");
        }
    }
    public List<String[]> leerYProcesarCSV(String nombreArchivo) throws IOException {
        List<String[]> registrosProcesados = new ArrayList<>();
        print("\n**[CSV] Iniciando PARSING de: " + nombreArchivo + "**");
        
        try (FileReader fr = new FileReader(nombreArchivo);
             BufferedReader br = new BufferedReader(fr)) {
            
            String linea;
            linea = br.readLine(); 
            
            print("--- Encabezado: " + linea + " ---");

            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(","); 
                
                if (campos.length == 3) {
                    try {
                        String matricula = campos[0].trim();
                        String nombre = campos[1].trim();
                        String calificacionStr = campos[2].trim();
                        
                        double calificacion = Double.parseDouble(calificacionStr); 
                        
                        print(String.format("**Registro: Matricula: %s | Nombre: %s | Calificacion: %.1f**", 
                                            matricula, nombre, calificacion));
                        
                        registrosProcesados.add(campos);
                        
                    } catch (NumberFormatException e) {
                        System.err.println("[CSV] Error de formato numerico en linea, ignorada: " + linea);
                    }
                } else {
                    System.err.println("[CSV] Error: Linea con numero incorrecto de campos, ignorada: " + linea);
                }
            }
        }
        print("\n**[CSV] Procesamiento finalizado. Registros validos: " + registrosProcesados.size() + "**");
        return registrosProcesados;
    }

    //backup
    public void crearBackup(String archivoFuente) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = LocalDateTime.now().format(dtf);

        String nombreDestino = "backup_" + timestamp + ".dat"; 
        
        Path origen = Paths.get(archivoFuente);
        Path destino = Paths.get(nombreDestino);
        try {
            Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
            print("\nBackup creado exitosamente: " + nombreDestino);
        } catch (NoSuchFileException e) {
            System.err.println("Error: El archivo fuente no existe. " + archivoFuente);
        } catch (IOException e) {
            throw e; 
        }
    }
}
