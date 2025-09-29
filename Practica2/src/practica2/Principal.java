package practica2;

import static lpoo.utils.Validar.*;


public class Principal{
    public static void main(String[] args){
        Universidad7528 uanl = new Universidad7528();

        EstudianteLS e1 = new EstudianteLS("Luis Segobia", "75281", 20, "LCC", 5);
        EstudianteLS e2 = new EstudianteLS("Claudia Sehinbaum", "75282");
        EstudianteLS e3 = new EstudianteLS("Jose Cuervo", "75283", 21);
        EstudianteLS e4 = new EstudianteLS("Aldo Adrian", "75284", 22, "MATEMATICAS", 9);
        EstudianteLS e5 = new EstudianteLS("Fernando Valenzuela", "75285");
       
        uanl.agregarEstudiante(e1);
        uanl.agregarEstudiante(e2);
        uanl.agregarEstudiante(e3);
        uanl.agregarEstudiante(e4);
        uanl.agregarEstudiante(e5);
        print("\n**Estudiantes creados:**");
        uanl.mostrarEstudiantes();
        pausar();
       
        String str = "\n***MENU PARA LA UANL***\n1.- Agregar estudiante\n2.- Buscar estudiante\n3.- Mostrar todos los estudiantes\n4.- Salir";
        
       int opcion; 
        
        EstudianteLS estudiante = null; 
      
        do{
            cleaner();
            opcion = menu(str, new int[] {1,2,3,4});
            switch(opcion){
            case 1->{
                cleaner();
                    if(validar_sn("\nTienes los 5 datos del estudiante?")){
                        String nombre = leer_string("\nIntroduzca el nombre del estudiante: ", 3, 256, false);
                        String matricula = leer_string("Introduzca la matricula del estudiante: ", 1, 256, true);
                        int edad = leer_entero("Introduzca la edad del estudiante: ", 15, 45);
                        String carrera = leer_enum("\nSelecciona una carrera: ", Carrera.class ).name();
                        int semestre = leer_entero("\nIntroduzca el semestre actual del estudiante: ", 1, 10);
                        estudiante = new EstudianteLS(nombre, matricula, edad, carrera, semestre);
                    }else{
                        String nombre = leer_string("\nIntroduzca el nombre del estudiante: ", 3, 256, false);
                        String matricula = leer_string("Introduzca la matricula del estudiante: ", 1, 256, true);
                        if(validar_sn("Tienes la edad del estudiante?")){
                            int edad = leer_entero("Introduzca la edad del estudiante: ", 12, 70);
                            estudiante = new EstudianteLS(nombre, matricula, edad);
                        }else{
                            estudiante = new EstudianteLS(nombre, matricula);
                        }
                    }
                    uanl.agregarEstudiante(estudiante);
                    cleaner();
                    print("\n**Estudiante creado con exito**");
                    estudiante.showEstudiante();
                    pausar();
                }
                case 2->{
                    cleaner();
                    print("\n**Buscando estudiante...**");
                    uanl.buscarEstudiante(leer_string("Introduzca nombre/matricula del estudiante: ", 3, 256, false));
                    pausar();

                }
                case 3->{
                    cleaner();
                    print("\n**Mostrando todos los estudiantes..**");
                    uanl.mostrarEstudiantes();
                    pausar();
                }
                case 4 ->{print("\nBye bye...");}
            }
        }while(opcion != 4);

    
    }
}

