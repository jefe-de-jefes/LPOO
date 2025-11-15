package com.gympos.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gympos.app.AppContext;
import com.gympos.app.Navigation;
import com.gympos.util.Threading;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox; 
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class GeneradorReportesController {
    
    @FXML private BorderPane root;
    @FXML private Label statusLabel;
    @FXML private ComboBox<String> reportTypeBox; 

    @FXML
    public void initialize() {
        reportTypeBox.setItems(FXCollections.observableArrayList(
            "Pagos", 
            "Clientes (General)", 
            "Membresías",
            "Accesos",
            "Inventario",
            "Clases",
            "Historial Respaldos"
        ));
        reportTypeBox.getSelectionModel().selectFirst();
    }

    @FXML
    private void handleGenerateReport() {
        String type = reportTypeBox.getValue();
        statusLabel.setStyle("-fx-text-fill: #F3F4F6;");
        statusLabel.setText("Generando reporte de " + type + "...");

        Threading.submitTask(() -> {
            try {
                switch (type) {
                    case "Pagos":
                        AppContext.reportService.generatePaymentsReport(
                            AppContext.paymentService.getAllPayments());
                        break;
                        
                    case "Clientes (General)":
                        AppContext.reportService.generateClientsReport(
                            AppContext.clientService.getAllClients());
                        break;
                        
                    case "Membresías":
                        AppContext.reportService.generateMembershipsReport(
                            AppContext.membershipService.getAllMemberships());
                        break;
                        
                    case "Accesos":
                        AppContext.reportService.generateAccessReport(
                            AppContext.accessService.getAllAccessLogs());
                        break;
                        
                    case "Inventario":
                        AppContext.reportService.generateInventoryReport(
                            AppContext.inventoryService.getAllItems());
                        break;
                        
                    case "Clases":
                        AppContext.reportService.generateScheduleReport(
                            AppContext.scheduleService.getAllClasses());
                        break;
                        
                    case "Historial Respaldos":
                        File backupDir = new File("backups");
                        List<String> backups = new ArrayList<>();
                        if (backupDir.exists() && backupDir.isDirectory()) {
                            String[] files = backupDir.list();
                            if (files != null) backups.addAll(Arrays.asList(files));
                        }
                        AppContext.reportService.generateBackupsReport(backups);
                        break;
                }

                Platform.runLater(() -> {
                    statusLabel.setStyle("-fx-text-fill: #10B981;");
                    statusLabel.setText("Reporte generado en carpeta /reports");
                });
                
            } catch (Exception e) {
                Platform.runLater(() -> {
                    statusLabel.setStyle("-fx-text-fill: #FCA5A5;");
                    statusLabel.setText("Error: " + e.getMessage());
                    e.printStackTrace();
                });
            }
        });
    }

    @FXML
    private void goToDashboard() {
        root.getScene().setRoot(Navigation.load("/com/gympos/view/dashboard.fxml"));
    }
}