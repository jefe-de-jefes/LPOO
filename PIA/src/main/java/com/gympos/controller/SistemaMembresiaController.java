package com.gympos.controller;

import java.time.LocalDate;

import com.gympos.app.AppContext;
import com.gympos.app.Navigation;
import com.gympos.model.Membership;
import com.gympos.model.RewardPoint;
import com.gympos.util.PopupUtils;
import com.gympos.util.Validation;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class SistemaMembresiaController { 
    
    @FXML private BorderPane root;
    @FXML private TextField clientIdField;
    @FXML private ChoiceBox<Membership.Type> membershipTypeBox;
    @FXML private Label statusLabel;

    @FXML private Label priceLabel;
    @FXML private CheckBox usePointsCheck;
    @FXML private Label expiryLabel;
    @FXML private Button btnCreate;

    @FXML
    public void initialize() {
        if (btnCreate != null) btnCreate.setDefaultButton(true);

        membershipTypeBox.setItems(FXCollections.observableArrayList(Membership.Type.values()));

        membershipTypeBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                updateCalculations();
            }
        });

        usePointsCheck.selectedProperty().addListener((obs, o, n) -> updateCalculations());
        
        clientIdField.textProperty().addListener((obs, o, n) -> {
            if (usePointsCheck.isSelected()) updateCalculations();
        });

        statusLabel.setStyle("-fx-text-fill: #F59E0B;");
        statusLabel.setText("Ingrese ID del cliente y seleccione tipo de membresía");
    }

    private void updateCalculations() {
        Membership.Type type = membershipTypeBox.getValue();
        if (type == null) {
            btnCreate.setDisable(true);
            btnCreate.setText("Seleccione Membresía");
            return;
        }

        btnCreate.setDisable(false);
        btnCreate.setText("Proceder al Pago");

        double price = AppContext.membershipService.getBasePrice(type);
        
        if (priceLabel != null) {
            priceLabel.setText(String.format("Precio Base: $%.2f", price));
        }

        LocalDate start = LocalDate.now();
        LocalDate end = AppContext.membershipService.calculateEndDate(start, type);
        
        if (expiryLabel != null) {
            expiryLabel.setText("Vence: " + end.toString());
        }

        if (usePointsCheck.isSelected()) {
            String clientId = clientIdField.getText();
            if (!Validation.isNullOrEmpty(clientId)) {
                try {
                     RewardPoint points = AppContext.rewardService.getPoints(clientId);
                    if (points != null && points.getPoints() > 0) {
                        statusLabel.setText("Puntos disponibles: " + points.getPoints() + ". Se aplicarán al pago.");
                        statusLabel.setStyle("-fx-text-fill: #10B981;");
                    } else {
                        statusLabel.setText("El cliente no tiene puntos acumulados.");
                        statusLabel.setStyle("-fx-text-fill: #F59E0B;");
                    }
                } catch (Exception e) {
                    // Ignorar error si no encuentra puntos aun
                }
            }
        } else {
            statusLabel.setText("");
        }
    }

    private void openPaymentPopup() {
        try {
            String clientId = clientIdField.getText();
            Membership.Type type = membershipTypeBox.getValue();

            if (Validation.isNullOrEmpty(clientId)) {
                statusLabel.setStyle("-fx-text-fill: #F59E0B;");
                statusLabel.setText("Ingrese ID del cliente.");
                return;
            }

            if (AppContext.clientService.getClientById(clientId) == null) {
                statusLabel.setStyle("-fx-text-fill: #F59E0B;");
                statusLabel.setText("Cliente no encontrado. Verifique el ID");
                return;
            }

            double basePrice = AppContext.membershipService.getBasePrice(type);
            
            double discount = 0.0;
            if (usePointsCheck.isSelected()) {
                RewardPoint points = AppContext.rewardService.getPoints(clientId);
                if (points != null && points.getPoints() > 0) {
                    // Lógica de descuento: 10 puntos = 1 unidad monetaria (ejemplo)
                    double discountAmount = points.getPoints() / 10.0; 
                    
                    double maxDiscount = 0.50; // Máximo 50% de descuento
                    double calculatedDiscount = discountAmount / basePrice;
                    
                    if (calculatedDiscount > maxDiscount) calculatedDiscount = maxDiscount;
                    discount = calculatedDiscount;
                }
            }

            Membership currentMembership = AppContext.membershipService.getCurrentMembership(clientId);
            boolean hasActiveMembership = currentMembership != null;
            String currentEndDate = hasActiveMembership ? currentMembership.getEnd().toString() : "";

            // AQUÍ ES DONDE SE LLAMA AL NUEVO CONTROLADOR
            ProcesadorPagosController controller = PopupUtils.showPaymentPopup(clientId, type, basePrice, discount, hasActiveMembership, currentEndDate);
            
            if (controller != null && controller.isPagoProcesado()) {
                statusLabel.setStyle("-fx-text-fill: #10B981;");
                statusLabel.setText("Membresía registrada exitosamente.");
                // Limpiar campos
                clientIdField.clear();
                membershipTypeBox.getSelectionModel().clearSelection();
                usePointsCheck.setSelected(false);
            }

        } catch (Exception e) {
            statusLabel.setStyle("-fx-text-fill: #FCA5A5;");
            statusLabel.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCreateMembership() {
        openPaymentPopup();
    }

    @FXML
    private void goToDashboard() {
        root.getScene().setRoot(Navigation.load("/com/gympos/view/dashboard.fxml"));
    }
}