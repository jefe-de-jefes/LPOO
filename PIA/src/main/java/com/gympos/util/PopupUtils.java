package com.gympos.util;

import java.io.IOException;
 
import com.gympos.controller.ProcesadorPagosController;
import com.gympos.model.Membership;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PopupUtils {
    
    public static ProcesadorPagosController showPaymentPopup(String clientId, Membership.Type type, double basePrice, double discount, boolean isRenewal, String currentEndDate) {
        try {
            // IMPORTANTE: Cargar el nuevo nombre del FXML
            FXMLLoader loader = new FXMLLoader(PopupUtils.class.getResource("/com/gympos/view/procesador_pagos.fxml"));
            Parent root = loader.load();
            
            // Obtener el controlador correcto
            ProcesadorPagosController controller = loader.getController();

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.UNDECORATED);
            popupStage.setTitle("Procesador de Pagos");

            Scene scene = new Scene(root);
            // Asegúrate de que la ruta al CSS es correcta
            scene.getStylesheets().add(PopupUtils.class.getResource("/com/gympos/css/app.css").toExternalForm());

            popupStage.setScene(scene);
            popupStage.setResizable(false);
            popupStage.centerOnScreen();

            // Pasar los datos al controlador
            controller.setPaymentData(clientId, type, basePrice, discount, isRenewal, currentEndDate);
            controller.setStage(popupStage);

            popupStage.showAndWait();

            return controller;

        } catch (IOException e) {
            throw new RuntimeException("Error al cargar el Procesador de Pagos", e);
        }
    }
}