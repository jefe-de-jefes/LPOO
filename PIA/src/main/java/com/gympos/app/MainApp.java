package com.gympos.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        AppContext.init();
        com.gympos.util.DataSeeder.seed();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gympos/view/login.fxml"));
        Scene scene = new Scene(loader.load(), 960, 600);
        scene.getStylesheets().add(getClass().getResource("/com/gympos/css/app.css").toExternalForm());
        stage.setTitle("GymPOS - Login");
        stage.getIcons().add(new Image("https://upload.wikimedia.org/wikipedia/commons/3/3a/Logo.png")); // Ícono genérico
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        AppContext.shutdown();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
