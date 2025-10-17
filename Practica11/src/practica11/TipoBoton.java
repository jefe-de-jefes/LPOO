package practica11;

public enum TipoBoton {
    MENU_CRUD("botones-crud"),
    ACCION_GUARDAR("boton-guardar"),
    ACCION_ACTUALIZAR("boton-actualizar"),
    ACCION_ELIMINAR("boton-eliminar"),
    ACCION_BUSCAR("boton-buscar");

    private final String estiloCss;

    TipoBoton(String estilo) {
        this.estiloCss = estilo;
    }

    public String getEstiloCss() {
        return estiloCss;
    }
}
