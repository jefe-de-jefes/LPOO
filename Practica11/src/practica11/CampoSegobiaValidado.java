package practica11;

import javafx.scene.control.TextField;

public class CampoSegobiaValidado extends TextField {
    
    private static final String ERROR_CLASS = "campo-invalido";
    private static final String VALID_CLASS = "campo-valido";
    
    public CampoSegobiaValidado(String promptText, String regex) {
        super();
        this.setPromptText(promptText);

        // Listener para validar cuando el usuario sale del campo
        this.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) { // Si pierde el foco
                validarContenido(regex);
            }
        });
        
        // Listener para quitar el estilo de error en cuanto el usuario empieza a corregir
        this.textProperty().addListener((obs, oldVal, newVal) -> {
            this.getStyleClass().remove(ERROR_CLASS);
        });
    }

    private void validarContenido(String regex) {
        // Limpiamos estilos previos
        this.getStyleClass().removeAll(ERROR_CLASS, VALID_CLASS);

        if (this.getText() == null || this.getText().trim().isEmpty() || !this.getText().matches(regex)) {
            this.getStyleClass().add(ERROR_CLASS); 
        } else {
            this.getStyleClass().add(VALID_CLASS); 
        }
    }
    
    public boolean esValido(String regex) {
        // Una validación final antes de usar el dato
        return this.getText() != null && !this.getText().trim().isEmpty() && this.getText().matches(regex);
    }
}
