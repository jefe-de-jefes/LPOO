package practica8;
import static lpoo.utils.Validar.*;

enum Categorias{
    LITERATURA,
    CIENCIA,
    HISTORIA,
    MATEMATICAS
}

public class Libro1410 implements Comparable<Libro1410> {
    private int id;
    private String titulo;
    private Categorias categoria;
    private int yearPublicacion;
    
    public Libro1410(int id, String titulo, String categoria, int yearPublicacion){
        setId(id);
        setTitulo(titulo);
        setCategoria(categoria);
        setYearPublicacion(yearPublicacion);
    }

    public void setId(int id){
        if (id <= 0) {
            throw new IllegalArgumentException("**Id invalido**");
        }
        this.id = id;
    }
    public void setTitulo(String titulo){
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("**Titulo invalido**");
        }
        this.titulo = titulo;
    }
    public void setCategoria(String categoria){
        try {
            this.categoria = Categorias.valueOf(normalName(categoria).replace(" ", ""));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("**Categoria invalida.");
        }
    }
    public void setYearPublicacion(int year){
        if(year <= 1800){
            throw new IllegalArgumentException("**El year es invalido**");
        }
        this.yearPublicacion = year;
    }

    public int getId(){
        return this.id;
    }
    public String getTitulo(){
        return this.titulo;
    }
    public Categorias getCategoria(){
        return this.categoria;
    }
    public int getYearPublicacion(){
        return this.yearPublicacion;
    }
   
    @Override
    public int compareTo(Libro1410 otro) {
        return this.titulo.compareTo(otro.titulo);
    }
    @Override
    public String toString() {
        return "Libro [ID=" + id + ", Título='" + titulo + "', Categoría=" + categoria 
               + ", Año=" + yearPublicacion + "]";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libro1410 libro = (Libro1410) o;
        return id == libro.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
