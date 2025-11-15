package com.gympos.app;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public final class Navigation {
    private Navigation() {}

    public static Parent load(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(Navigation.class.getResource(fxmlPath));
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Error de E/S al cargar FXML: " + fxmlPath, e);
        } catch (IllegalStateException e) {
            throw new RuntimeException("Error de estado al cargar FXML: " + fxmlPath, e);
        }
    }
}
