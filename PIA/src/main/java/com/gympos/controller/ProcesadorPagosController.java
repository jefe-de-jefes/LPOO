package com.gympos.controller;

import java.time.LocalDate;

import com.gympos.app.AppContext;
import com.gympos.model.Client;
import com.gympos.model.Membership;
import com.gympos.model.Payment;
import com.gympos.util.Validation;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ProcesadorPagosController {
    
    @FXML private Label clientIdLabel;
    @FXML private Label clientNameLabel;
    @FXML private Label membershipTypeLabel;
    @FXML private Label basePriceLabel;
    @FXML private Label discountLabel;
    @FXML private Label totalAmountLabel;
    @FXML private Label vigenciaLabel;
    @FXML private ChoiceBox<String> methodBox;
    @FXML private Label statusLabel;
    
    private String clientId;
    private Membership.Type membershipType;
    private double basePrice;
    private double discount;
    private double finalAmount;
    private Stage popupStage;
    private boolean isRenewal = false;
    private boolean pagoProcesado = false;

    public boolean isPagoProcesado() { return pagoProcesado; }
    
    @FXML
    public void initialize() {
        methodBox.setItems(FXCollections.observableArrayList("CASH", "CARD"));
        methodBox.getSelectionModel().selectFirst();
    }
    
    public void setPaymentData(String clientId, Membership.Type type, double basePrice, double discount, boolean isRenewal, String currentEndDate) {
        this.clientId = clientId;
        this.membershipType = type;
        this.basePrice = basePrice;
        this.discount = discount;
        this.finalAmount = basePrice * (1.0 - discount);
        this.isRenewal = isRenewal;
        
        Client client = AppContext.clientService.getClientById(clientId);
        
        clientIdLabel.setText(clientId);
        clientNameLabel.setText(client != null ? client.getFullName() : "No encontrado");
        membershipTypeLabel.setText(type.toString());
        basePriceLabel.setText(String.format("$%.2f", basePrice));
        discountLabel.setText(String.format("%.1f%%", discount * 100));
        totalAmountLabel.setText(String.format("$%.2f", finalAmount));
        
        String vigenciaText;
        if (isRenewal) {
            LocalDate currentEnd = LocalDate.parse(currentEndDate);
            LocalDate newEnd = AppContext.membershipService.calculateEndDate(currentEnd, type);
            vigenciaText = "Extender hasta: " + newEnd.toString();
            statusLabel.setStyle("-fx-text-fill: #F59E0B;");
            statusLabel.setText("EXTENSIÓN DE MEMBRESÍA");
        } else {
            LocalDate end = AppContext.membershipService.calculateEndDate(LocalDate.now(), type);
            vigenciaText = "Nueva vigencia hasta: " + end.toString();
            statusLabel.setStyle("-fx-text-fill: #10B981;");
            statusLabel.setText("NUEVA MEMBRESÍA");
        }
        vigenciaLabel.setText(vigenciaText);
    }
    
    public void setStage(Stage stage) { 
        this.popupStage = stage; 
    }
    
    @FXML
    private void handleProcessPayment() {
        try {
            String method = methodBox.getValue();
            if (Validation.isNullOrEmpty(method)) throw new Exception("Seleccione método.");

            String paymentId = "P-" + System.currentTimeMillis();
            Payment payment = new Payment(paymentId, clientId, finalAmount, method);
            AppContext.paymentService.processPayment(payment);

            if (isRenewal) {
                AppContext.membershipService.renewMembership(clientId, membershipType, finalAmount);
            } else {
                AppContext.membershipService.createMembership(clientId, membershipType, discount);
            }

            Client client = AppContext.clientService.getClientById(clientId);
            if (client != null && !client.isActive()) {
                client.setActive(true);
                AppContext.clientService.updateClient(client);
            }

            if (discount > 0) {
                double discountValue = basePrice * discount;
                int pointsToRedeem = (int) Math.ceil(discountValue * 10);
                AppContext.rewardService.redeemPoints(clientId, pointsToRedeem);
            }

            int pointsEarned = (int) (finalAmount / 100);
            if (pointsEarned > 0) {
                AppContext.rewardService.addPoints(clientId, pointsEarned);
            }

            statusLabel.setText("¡Pago Exitoso!");
            pagoProcesado = true;
            
            new Thread(() -> {
                try { Thread.sleep(1000); } catch (Exception e) {}
                javafx.application.Platform.runLater(this::handleClose);
            }).start();

        } catch (Exception e) {
            statusLabel.setStyle("-fx-text-fill: #FCA5A5;");
            statusLabel.setText("Error: " + e.getMessage());
        }
    }
    
    @FXML private void handleCancel() { handleClose(); }
    @FXML private void handleClose() { if (popupStage != null) popupStage.close(); }
}