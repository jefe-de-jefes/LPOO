// Archivo: src/practica11/Boton14Estilizado.java
package practica11;

import javafx.scene.control.Button;

public class Boton14Estilizado extends Button {

    public Boton14Estilizado(String texto, TipoBoton tipo) {
        super(texto);

        // Añade la clase CSS base para todos los botones
        this.getStyleClass().add("boton-base");

        // ¡ESTA ES LA LÓGICA CORRECTA!
        // Añade la clase CSS específica según el tipo que recibe.
        if (tipo != null) {
            this.getStyleClass().add(tipo.getEstiloCss());
        }

        this.setMaxWidth(Double.MAX_VALUE);

        // Efecto de escala al pasar el mouse
        this.setOnMouseEntered(e -> {
            this.setScaleX(1.05);
            this.setScaleY(1.05);
        });

        this.setOnMouseExited(e -> {
            this.setScaleX(1.0);
            this.setScaleY(1.0);
        });
    }
}
