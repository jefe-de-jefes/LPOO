package com.gympos.controller;

import java.util.Optional;

import com.gympos.app.AppContext;
import com.gympos.app.Navigation;
import com.gympos.model.Client;
import com.gympos.model.Membership;
import com.gympos.util.Exceptions.ValidationException;
import com.gympos.util.Validation;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class GestionClientesController {
    
    @FXML private BorderPane root;
    @FXML private TableView<Client> clientTable;
    @FXML private TableColumn<Client, String> colId;
    @FXML private TableColumn<Client, String> colFullName;
    @FXML private TableColumn<Client, String> colEmail;
    @FXML private TableColumn<Client, String> colPhone;
    @FXML private TableColumn<Client, String> colStatus;
    @FXML private TableColumn<Client, String> colExpiry; 

    @FXML private TextField searchField;
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private Label errorLabel;
    @FXML private Button btnAdd; 

    private ObservableList<Client> clientList;
    private FilteredList<Client> filteredData; 

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("statusString")); 

        colExpiry.setCellValueFactory(cellData -> {
            String clientId = cellData.getValue().getId();
            Membership m = AppContext.membershipService.getCurrentMembership(clientId);
            
            if (m != null) {
                return new SimpleStringProperty(m.getEnd().toString());
            } else {
                return new SimpleStringProperty("---");
            }
        });

        clientList = FXCollections.observableArrayList(AppContext.clientService.getAllClients());
        
        filteredData = new FilteredList<>(clientList, p -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(client -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                
                if (client.getId().toLowerCase().contains(lowerCaseFilter)) return true;
                if (client.getFullName().toLowerCase().contains(lowerCaseFilter)) return true;
                return false;
            });
        });

        clientTable.setItems(filteredData);

        idField.setDisable(true);
        idField.setPromptText("Automático");
        if (btnAdd != null) btnAdd.setDefaultButton(true);

        clientTable.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) populateForm(newSelection);
            });
    }

    @FXML
    private void handleAddClient() {
        try {
            String clientId = AppContext.clientService.generateNextId();
            if (Validation.isNullOrEmpty(nameField.getText()) || Validation.isNullOrEmpty(emailField.getText())) {
                throw new ValidationException("Nombre y Email obligatorios.");
            }

            if (!Validation.isValidEmail(emailField.getText())) {
                throw new ValidationException("Formato de email inválido (ej: usuario@dominio.com).");
            }
            Client newClient = new Client(clientId, nameField.getText(), emailField.getText(), phoneField.getText());
            
            AppContext.clientService.addClient(newClient);
            clientList.add(newClient);
            clearForm();
            errorLabel.setText("Guardado: " + clientId);
            errorLabel.setStyle("-fx-text-fill: #10B981;");
        } catch (Exception e) {
            errorLabel.setText("Error: " + e.getMessage());
            errorLabel.setStyle("-fx-text-fill: #FCA5A5;");
        }
    }

    @FXML
    private void handleUpdateClient() {
        Client selected = clientTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        if (!Validation.isValidEmail(emailField.getText())) {
                throw new ValidationException("Formato de email inválido (ej: usuario@dominio.com).");
        }
        
        selected.setFullName(nameField.getText());
        selected.setEmail(emailField.getText());
        selected.setPhone(phoneField.getText());
        
        AppContext.clientService.updateClient(selected);
        clientTable.refresh();
        errorLabel.setText("Actualizado.");
        errorLabel.setStyle("-fx-text-fill: #10B981;");
    }

    @FXML
    private void handleDeleteClient() {
        Client selected = clientTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        if (!selected.isActive()) {
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("Acción no permitida");
            warning.setHeaderText("Cliente ya está inactivo");
            warning.setContentText("No se puede dar de baja a un cliente que ya se encuentra en estatus Inactivo.");
            warning.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Baja");
        alert.setHeaderText("¿Desactivar a " + selected.getFullName() + "?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            AppContext.clientService.deactivateClient(selected.getId());
            
            AppContext.membershipService.cancelMembership(selected.getId());

            clientTable.refresh(); 
            
            clearForm();
            errorLabel.setText("Cliente desactivado y membresía cancelada.");
            errorLabel.setStyle("-fx-text-fill: #F59E0B;");
        }
    }

    private void populateForm(Client client) {
        idField.setText(client.getId());
        nameField.setText(client.getFullName());
        emailField.setText(client.getEmail());
        phoneField.setText(client.getPhone());
    }

    private void clearForm() {
        idField.clear(); nameField.clear(); emailField.clear(); phoneField.clear();
        clientTable.getSelectionModel().clearSelection();
    }
    
    @FXML
    private void goToDashboard() {
        root.getScene().setRoot(Navigation.load("/com/gympos/view/dashboard.fxml"));
    }
}