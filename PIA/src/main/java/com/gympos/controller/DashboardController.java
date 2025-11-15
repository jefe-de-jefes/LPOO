package com.gympos.controller;

import java.util.Optional;

import com.gympos.app.AppContext;
import com.gympos.app.Navigation;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class DashboardController {

    @FXML
    private BorderPane root;
    
    @FXML
    private Label timeLabel;
    
    @FXML
    private Label dateLabel;
    
    private Timeline clockTimeline;

    @FXML
    private void goToGestionClientes() {
        root.getScene().setRoot(Navigation.load("/com/gympos/view/gestion_clientes.fxml"));
    }

    @FXML
    private void goToSistemaMembresias() {
        root.getScene().setRoot(Navigation.load("/com/gympos/view/sistema_membresias.fxml"));
    }

    @FXML
    private void goToControlAcceso() {
        root.getScene().setRoot(Navigation.load("/com/gympos/view/control_acceso.fxml"));
    }

    @FXML
    private void goToGeneradorReportes() {
        root.getScene().setRoot(Navigation.load("/com/gympos/view/generador_reportes.fxml"));
    }

    @FXML
    private void goToProcesadorPagos() {
        root.getScene().setRoot(Navigation.load("/com/gympos/view/procesador_pagos.fxml"));
    }

    @FXML
    private void goToInventario() {
        root.getScene().setRoot(Navigation.load("/com/gympos/view/inventario.fxml"));
    }


    @FXML
    private void goToGestionEmpleados() {
        if (!"admin".equals(AppContext.activeUser.getUsername())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Acceso Restringido");
            alert.setHeaderText("Permisos Insuficientes");
            alert.setContentText("Solo el usuario Administrador puede gestionar empleados.");
            alert.showAndWait();
            return;
        }

        if (confirmarIdentidad()) {
            root.getScene().setRoot(Navigation.load("/com/gympos/view/gestion_empleados.fxml"));
        }
    }

    private boolean confirmarIdentidad() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Seguridad");
        dialog.setHeaderText("Confirmar Credenciales de Admin");
        dialog.setContentText("Por favor, confirme su contraseña:");

        PasswordField pwd = new PasswordField();
        pwd.setPromptText("Contraseña");
        
        VBox content = new VBox(10);
        content.getChildren().add(new Label("Ingrese contraseña de Admin:"));
        content.getChildren().add(pwd);
        dialog.getDialogPane().setContent(content);

        ButtonType loginButtonType = new ButtonType("Acceder", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return pwd.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            String inputPass = result.get();
            try {
                AppContext.authService.login(AppContext.activeUser.getUsername(), inputPass);
                return true;
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error de Seguridad");
                alert.setHeaderText("Acceso Denegado");
                alert.setContentText("Contraseña incorrecta.");
                alert.showAndWait();
                return false;
            }
        }
        return false;
    }

    @FXML
    private void goToCalendario() {
        root.getScene().setRoot(Navigation.load("/com/gympos/view/schedules.fxml"));
    }

    @FXML
    private void handleManualBackup() {
        try {
            AppContext.backupService.manualBackup();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Respaldo del Sistema");
            alert.setHeaderText(null);
            alert.setContentText("Copia de seguridad creada exitosamente en la carpeta 'backups'.");
            alert.showAndWait();
            
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Respaldo");
            alert.setHeaderText("No se pudo crear el respaldo");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    
    @FXML
    public void initialize() {
        startClock();
    }
    
    private void startClock() {
        clockTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateClock()));
        clockTimeline.setCycleCount(Animation.INDEFINITE);
        clockTimeline.play();
        updateClock(); 
    }
    
    private void updateClock() {
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        java.time.format.DateTimeFormatter timeFormatter = java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss");
        java.time.format.DateTimeFormatter dateFormatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        timeLabel.setText(now.format(timeFormatter));
        dateLabel.setText(now.format(dateFormatter));
    }
    
    public void stopClock() {
        if (clockTimeline != null) {
            clockTimeline.stop();
        }
    }

    @FXML
    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar Sesión");
        alert.setHeaderText("¿Desea salir del sistema?");
        
        if (alert.showAndWait().get() == ButtonType.OK) {
            stopClock();
            
            AppContext.activeUser = null;
            
            ((javafx.stage.Stage) root.getScene().getWindow()).setTitle("GymPOS - Login");
            root.getScene().setRoot(Navigation.load("/com/gympos/view/login.fxml"));
        }
    }
}