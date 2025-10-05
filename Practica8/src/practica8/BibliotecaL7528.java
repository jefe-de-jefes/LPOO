package practica8;

import static lpoo.utils.Validar.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Iterator;
import java.util.Collections;
import java.util.Comparator;
import java.time.Duration; 

public class BibliotecaL7528 { 
    
    private final ArrayList<Libro1410> librosDisponibles; 
    
    private final LinkedList<String> colaReservas; 
    
    private final HashMap<String, String> usuariosRegistrados; 
    
    private final HashSet<String> categoriasUnicas; 

    public BibliotecaL7528() {
        this.librosDisponibles = new ArrayList<>();
        this.colaReservas = new LinkedList<>();
        this.usuariosRegistrados = new HashMap<>();
        this.categoriasUnicas = new HashSet<>();
    }


    public void agregarLibro(Libro1410 libro) {
        if (librosDisponibles.contains(libro)) {
             throw new IllegalArgumentException("Error: El libro con ID " + libro.getId() + " ya existe.");
        }
        librosDisponibles.add(libro);
        categoriasUnicas.add(libro.getCategoria().name());
    }

    public Libro1410 buscarLibroPorId(int id) {
        return librosDisponibles.stream()
            .filter(l -> l.getId() == id)
            .findFirst() 
            .orElse(null);
    }

    public boolean actualizarYearLibro(int id, int nuevoYear) {
        Libro1410 libro = buscarLibroPorId(id);
        if (libro != null) {
            libro.setYearPublicacion(nuevoYear);
            return true;
        }
        return false;
    }

    public boolean eliminarLibro(int id) {
        Libro1410 libro = buscarLibroPorId(id);
        if (libro != null) {
            return librosDisponibles.remove(libro); 
        }
        return false;
    }
    
    public String obtenerProximaReserva() {
        return colaReservas.peekFirst();
    }

    public int contarReservas() {
        return colaReservas.size();
    }

    public int limpiarCatalogoAntiguo(int yearLimite) {
        Iterator<Libro1410> it = librosDisponibles.iterator();
        int eliminados = 0;
        while (it.hasNext()) {
            Libro1410 libro = it.next();
            if (libro.getYearPublicacion() < yearLimite) {
                it.remove();
                eliminados++;
            }
        }
        return eliminados;
    }

    public void registrarReserva(String matriculaUsuario) {
        if (!usuariosRegistrados.containsKey(matriculaUsuario)) {
            throw new IllegalArgumentException("Error: La matrícula '" + matriculaUsuario + "' no esta registrada. No se puede reservar.");
        }
        
        colaReservas.addLast(matriculaUsuario);
        print("Reserva registrada para: " + matriculaUsuario);
    }

    public String procesarSiguienteReserva() {
        if (colaReservas.isEmpty()) {
            return "No hay reservas pendientes.";
        }
        return colaReservas.pollFirst();
    }
    
    public List<Libro1410> filtrarLibrosPorCategoria(Categorias categoria) {
        return librosDisponibles.stream()
            .filter(l -> l.getCategoria() == categoria)
            .collect(Collectors.toList());
    }
    
    public String obtenerLibroMasAntiguo() {
        return librosDisponibles.stream()
            .min(Comparator.comparingInt(Libro1410::getYearPublicacion))
            .map(Libro1410::getTitulo)
            .orElse("No hay libros.");
    }

    public List<Libro1410> obtenerLibrosOrdenadosPorTitulo() {
        List<Libro1410> listaOrdenada = new ArrayList<>(librosDisponibles);
        Collections.sort(listaOrdenada);
        return listaOrdenada;
    }

    public List<Libro1410> obtenerLibrosOrdenadosPorYearDesc() {
        List<Libro1410> listaOrdenada = new ArrayList<>(librosDisponibles);
        listaOrdenada.sort((l1, l2) -> Integer.compare(l2.getYearPublicacion(), l1.getYearPublicacion()));
        return listaOrdenada;
    }

    public void medirTiempoBusqueda(int idBuscado) {
        long inicio = System.nanoTime();
        buscarLibroPorId(idBuscado);
        long fin = System.nanoTime();
        
        Duration duracion = Duration.ofNanos(fin - inicio);
        print("Tiempo de busqueda lineal: " + duracion.toNanos() + " microsegundos");
    }
    
    public void medirTiempoRegistroUsuario(String matricula, String nombre) {
        long inicio = System.nanoTime();
        registrarUsuario(matricula, nombre);
        long fin = System.nanoTime();

        Duration duracion = Duration.ofNanos(fin - inicio);
        print("Tiempo de registro: "+ duracion.toNanos() + " nanosegundos%n");
    }

    public void registrarUsuario(String matricula, String nombre) {
        if(!validar_long_str(matricula, 7, 7, true)){
            print("**Matricula no valida**");
            return;
        };
        if (usuariosRegistrados.containsKey(matricula)) {
            throw new IllegalArgumentException("Error: La matricula '" + matricula+ "' ya esta registrada. No se puede agregar.");
        }
        usuariosRegistrados.put(matricula, nombre);
        print("Usuario "+ nombre + " registrado con exito.**");
    }

    public Set<String> getCategoriasUnicas() {
        return categoriasUnicas;
    }

    public ArrayList<Libro1410> getLibrosDisponibles() {
        return librosDisponibles;
    }
}
