package practica8;

import static lpoo.utils.Validar.*;
import java.util.List;
import java.util.Set; 

import java.time.LocalDate;

public class Main{

    private static void cargarDatosIniciales(BibliotecaL7528 biblio) {
        biblio.agregarLibro(new Libro1410(1, "Estructura de Datos Avanzadas", "CIENCIA", 2024));
        biblio.agregarLibro(new Libro1410(2, "La Historia de Monterrey", "HISTORIA", 1995));
        biblio.agregarLibro(new Libro1410(3, "Introduccion al Calculo I", "MATEMATICAS", 2005));
        biblio.agregarLibro(new Libro1410(4, "Cien Years de Soledad", "LITERATURA", 1967));
        biblio.agregarLibro(new Libro1410(5, "Astrologia y la Ciencia", "CIENCIA", 2020));
        
        String miMatricula = "2177528"; 
        biblio.registrarUsuario(miMatricula, "Luis Segobia");
        biblio.registrarUsuario("9638527", "Juan Perez");
        biblio.registrarUsuario("7418523", "Ana Gomez");
        
        biblio.registrarReserva("9638527");
        biblio.registrarReserva(miMatricula);
    }
    
    public static void main(String[] args) {
        BibliotecaL7528 biblioteca = new BibliotecaL7528(); 
        int opcion = -1;

        cargarDatosIniciales(biblioteca);
        print("\nDatos iniciales cargados con exito");
        pausar();

        do {
            cleaner();
            try {
                String menuPrincipal = "\n=============================================\n" +
                                       "   BIBLIOTECA L7528 - MENU PRINCIPAL\n" +
                                       "=============================================\n" +
                                       "1. Operaciones CRUD de libros\n" +
                                       "2. Gestion de reservas\n" +
                                       "3. Busqueda y filtrado\n" +
                                       "4. Ordenamiento\n" +
                                       "5. Analisis de complejidad temporal\n" +
                                       "0. Salir\n" +
                                       "=============================================\n" +
                                       "Seleccione una opcion> ";
                opcion = menu(menuPrincipal, new int[] {0,1,2,3,4,5});

                switch (opcion) {
                    case 1: crudLibrosMenu(biblioteca); break;
                    case 2: gestionReservas(biblioteca); break;
                    case 3: busquedaYFiltros(biblioteca); break; 
                    case 4: ordenamientoYComparators(biblioteca); break;
                    case 5: analisisComplejidad(biblioteca); break;
                    case 0: print("\nSaliendo del Programa. Gracias por usar tu biblio de preferencia. Bye bye.");
                        break;
                    default:
                        print("Opcion no valida. Intente de nuevo.");
                }
            } catch (Exception e) {
                System.err.println("ERROR INESPERADO: " + e.getMessage());
                pausar();
            }
            pausar();
        } while (opcion != 0);
    }


    private static void crudLibrosMenu(BibliotecaL7528 biblio) {
        int opcion;
        do {
            cleaner();
            String menuCRUD = "\n--- CRUD DE LIBROS ---\n" +
                                    "1. Agregar libro\n" +
                                    "2. Buscar libro por ID\n" +
                                    "3. Actualizar year de publicacion\n" +
                                    "4. Eliminar libro\n" +
                                    "5. Avanzado: limpiar catalogo antiguo\n" +
                                    "0. Volver al menu principal\n" + 
                                    "Seleccione una opcion> ";
            opcion = menu(menuCRUD, new int[] {0,1,2,3,4,5});
            try {
                int id, year;
                
                switch (opcion) {
                    case 1:
                        print("\n**Agregar libro**");
                        id = leer_entero("Id> ", 1, 99999999);
                        String titulo = leer_string("Titulo> ", 3, 256, false);
                        String cat = leer_enum("Categoria> ", Categorias.class).name();
                        year = leer_entero("Year de publicacion> ", 1800, LocalDate.now().getYear());
                        
                        Libro1410 nuevo = new Libro1410(id, titulo, cat, year);
                        biblio.agregarLibro(nuevo); 
                        print("\nLibro agregado.");
                        pausar();
                        break;
                        
                    case 2:
                        cleaner();
                        print("\n**Busqueda de libro**");
                        id = leer_entero("Id del libro a buscar> ", 1, 99999999);
                        Libro1410 encontrado = biblio.buscarLibroPorId(id);
                        print("\n" + encontrado != null ? "Encontrado: " + encontrado : "Libro no encontrado.");
                        pausar();
                        break;
                        
                    case 3:
                        cleaner();
                        print("\n**Actualizar year de publicacion.**");
                        id = leer_entero("Id del libro a actualizar> ", 1, 99999999);
                        year = leer_entero("Nuevo year de publicacion> ", 1800, LocalDate.now().getYear());
                        boolean actualizado = biblio.actualizarYearLibro(id, year);
                        print("\n" + (actualizado ? "Year actualizado." : "ID no encontrado."));
                        pausar();
                        break;
                        
                    case 4:
                        cleaner();
                        print("\n**Eliminar libro**");
                        id = leer_entero("Id del libro a eliminar> ",  1, 99999999);
                        boolean eliminado = biblio.eliminarLibro(id);
                        print("\n" + (eliminado ? "Libro eliminado." : "ID no encontrado."));
                        pausar();
                        break;
                        
                    case 5: 
                        int yearLimite = leer_entero("Introduzca el a partir de que year desea conservar los libros (se eliminaran los demas)> ", 1801, LocalDate.now().getYear());
                        print("\nEliminando libros publicados antes de " + yearLimite + "...");
                        int count = biblio.limpiarCatalogoAntiguo(yearLimite); 
                        print("\nSe eliminaron " + count + " libros antiguos. (Manejo de Iteradores)");
                        pausar();
                        break;
                }
            } catch (Exception e) {
                System.err.println("ERROR INESPERADO: " + e.getMessage());
                pausar();
            }
        } while (opcion != 0);
    }

    
    private static void gestionReservas(BibliotecaL7528 biblio) {
        int opcion;
        do {
            cleaner();
            String menuReserva = "\n--- GESTION DE RESERVAS ---\n" +
                                  "1. Registrar nueva reserva\n" +
                                  "2. Mostrar proxima reserva\n" +
                                  "3. Procesar/Atender siguiente reserva\n" +
                                  "4. Mostrar conteo de reservas\n" +
                                  "0. Volver al menu principal\n" +
                                  "Opcion> ";
            opcion = menu(menuReserva, new int[] {0, 1, 2, 3, 4});

            try {
                String matricula;
                switch (opcion) {
                    case 1: 
                        matricula = leer_string("Ingrese la matricula del usuario a reservar> ", 7, 7, true);
                        biblio.registrarReserva(matricula); 
                        print("Reserva registrada. Se agrego al final de la cola.");
                        pausar();
                        break;

                    case 2: 
                        matricula = biblio.obtenerProximaReserva();
                        print("\nProxima matricula en la cola: " + 
                              (matricula != null ? matricula : "La cola de reservas esta vacia."));
                        pausar();
                        break;

                    case 3:
                        String procesado = biblio.procesarSiguienteReserva();
                        print("\nReserva atendida:\n" + procesado);
                        pausar();
                        break;

                    case 4: 
                        print("\nReservas pendientes: " + biblio.contarReservas());
                        pausar();
                        break;
                }
            } catch (Exception e) {
                System.err.println("ERROR: " + e.getMessage());
                pausar();
            }

        } while (opcion != 0);
    }


    
    private static void busquedaYFiltros(BibliotecaL7528 biblio) {
        int opcion;
        do{
            cleaner();
            String menuBusqueda =  "---- BUSQUEDA Y FILTROS ----\n" +
                                  "1. Mostrar categorias unicas\n" +
                                  "2. Encontrar el Libro mas antiguo\n" +
                                  "3. Filtrar libros por categoria\n" +
                                  "0. Volver al menu principal\n" +
                                  "Opcion> ";
            opcion = menu(menuBusqueda, new int[] {0,1,2,3});
            
            try {
                switch (opcion) {
                    case 1:
                        Set<String> categorias = biblio.getCategoriasUnicas(); 
                        print("\nCategorias unicas registradas:\n" + categorias);
                        pausar();
                        break;
                        
                    case 2:
                        String tituloMasAntiguo = biblio.obtenerLibroMasAntiguo();
                        print("\nLibro mas antiguo: " + tituloMasAntiguo);
                        pausar();
                        break;
                        
                    case 3:
                        String catStr = leer_enum("Categoria a filtrar> ", Categorias.class).name();
                        
                        Categorias cat = Categorias.valueOf(catStr); 
                        
                        List<Libro1410>filtrados = biblio.filtrarLibrosPorCategoria(cat); 
                        
                        if (filtrados.isEmpty()) {
                            print("\nNo se encontraron libros en la categoria: " + catStr);
                        } else {
                            print("\nLibros encontrados (" + catStr + "):");
                            filtrados.forEach(l -> print("   - " + l));
                        }
                        pausar();
                        break;
                }
            } catch (Exception e) {
                System.err.println("\nERROR: " + e.getMessage());
                pausar();
            }
            
            if (opcion == 0){
                print("\nRegresando a menu principal...");
            }
            
        } while (opcion != 0);
    }


    private static void ordenamientoYComparators(BibliotecaL7528 biblio) {
        int opcion = -1;

        do {
            cleaner();
            try {
                String menuOrdenamiento = "\n--- ORDENAMIENTO ---\n" +
                                         "1. Ordenados por titulo \n" +
                                         "2. Ordenados por year descendente \n" +
                                         "0. Volver al menu principal\n" +
                                         "Seleccione una opcion> ";

                opcion = menu(menuOrdenamiento, new int[] {0,1,2});

                switch (opcion) {
                    case 1:
                        print("\n--- Libros ordenados por titulo ---");
                        biblio.obtenerLibrosOrdenadosPorTitulo()
                              .forEach(l -> print("\n   - " + l.getTitulo()));
                        pausar();
                        break;

                    case 2:
                        print("\n--- Libros ordenados por year descendente ---");
                        biblio.obtenerLibrosOrdenadosPorYearDesc()
                              .forEach(l -> print("\n   - " + l.getYearPublicacion() + " - " + l.getTitulo()));
                        pausar();
                        break;

                    case 0:
                        print("\nVolviendo al menu principal...");
                        break;

                    default:
                        print("Opcion no valida. Intente de nuevo.");
                        pausar();
                }
            } catch (Exception e) {
                System.err.println("ERROR INESPERADO: " + e.getMessage());
                pausar();
            }
        } while (opcion != 0);
    }


    private static void analisisComplejidad(BibliotecaL7528 biblio) {
        int opcion = -1;

        do {
            cleaner();
            try {
                String menuComplejidad = "\n--- ANALISIS DE TIEMPO ---\n" +
                                         "1. Busqueda por ID O(n)\n" +
                                         "2. Registro de Usuario O(1)\n" +
                                         "0. Volver al menu principal\n" +
                                         "Seleccione una opcion> ";

                opcion = menu(menuComplejidad, new int[]{0,1,2});

                switch (opcion) {
                    case 1:
                        print("\n\n--- Busqueda lineal por id ---");
                        int idBuscado = leer_entero("Introduzca el id del libro a buscar> ", 1, 99999999);
                        biblio.medirTiempoBusqueda(idBuscado); 
                        pausar();
                        break;

                    case 2:
                        print("\n\n--- Registro de usuario ---");
                        String matricula = leer_string("Matricula> ", 7, 7, true);
                        String nombreInput = leer_string("Nombre de usuario> ", 3, 256, false);
                        String nombre = validarName(nombreInput, 3, 256);
                        biblio.medirTiempoRegistroUsuario(matricula, nombre);
                        pausar();
                        break;

                    case 0:
                        print("\n\nVolviendo al menu principal...");
                        break;

                    default:
                        print("\nOpcion no valida. Intente de nuevo.");
                }
            } catch (Exception e) {
                System.err.println("ERROR INESPERADO: " + e.getMessage());
                pausar();
            }
        } while (opcion != 0);
        
    }
}
