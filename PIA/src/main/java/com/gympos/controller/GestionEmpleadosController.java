package com.gympos.controller;

import java.util.Optional;
import java.util.UUID;

import com.gympos.app.AppContext;
import com.gympos.app.Navigation;
import com.gympos.model.Employee;
import com.gympos.service.AuthService;
import com.gympos.util.Validation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class GestionEmpleadosController {

    @FXML private BorderPane root;
    @FXML private TableView<Employee> table;
    @FXML private TableColumn<Employee, String> colId;
    @FXML private TableColumn<Employee, String> colName;
    @FXML private TableColumn<Employee, String> colUsername;
    
    @FXML private TextField nameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    
    private ObservableList<Employee> employeeList;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));

        employeeList = FXCollections.observableArrayList(AppContext.authService.getAllEmployees());
        table.setItems(employeeList);

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nameField.setText(newSelection.getName());
                usernameField.setText(newSelection.getUsername());
                passwordField.clear(); // Por seguridad, no mostramos el hash
                passwordField.setPromptText("Dejar vacío para no cambiar");
            }
        });
    }

    @FXML
    private void handleAdd() {
        try {
            if (Validation.isNullOrEmpty(nameField.getText()) || 
                Validation.isNullOrEmpty(usernameField.getText()) ||
                Validation.isNullOrEmpty(passwordField.getText())) {
                throw new RuntimeException("Todos los campos son obligatorios para crear un usuario.");
            }

            String id = UUID.randomUUID().toString().substring(0, 8);
            
            Employee newEmp = new Employee(
                id, 
                nameField.getText(), 
                usernameField.getText(), 
                passwordField.getText()
            );

            AppContext.authService.addEmployee(newEmp);
            employeeList.add(newEmp);
            
            clearForm();
            errorLabel.setStyle("-fx-text-fill: #10B981;");
            errorLabel.setText("Empleado agregado correctamente.");

        } catch (Exception e) {
            errorLabel.setStyle("-fx-text-fill: #FCA5A5;");
            errorLabel.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdate() {
        Employee selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            String newName = nameField.getText();
            String newUser = usernameField.getText();
            String newPass = passwordField.getText();

            if (Validation.isNullOrEmpty(newName) || Validation.isNullOrEmpty(newUser)) {
                throw new RuntimeException("Nombre y Usuario no pueden estar vacíos.");
            }

            String passwordToSave = selected.getPasswordHash();
            
            if (!Validation.isNullOrEmpty(newPass)) {
                passwordToSave = AuthService.hashPassword(newPass);
            }

            Employee updatedEmp = new Employee(selected.getId(), newName, newUser, passwordToSave);
            
            AppContext.authService.updateEmployee(updatedEmp);
            
            int index = employeeList.indexOf(selected);
            employeeList.set(index, updatedEmp);
            
            clearForm();
            errorLabel.setStyle("-fx-text-fill: #10B981;");
            errorLabel.setText("Empleado actualizado.");

        } catch (Exception e) {
            errorLabel.setStyle("-fx-text-fill: #FCA5A5;");
            errorLabel.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        Employee selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        if (selected.getUsername().equals("admin")) {
            errorLabel.setStyle("-fx-text-fill: #FCA5A5;");
            errorLabel.setText("No se puede eliminar al usuario 'admin' principal.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Eliminación");
        alert.setHeaderText("¿Eliminar empleado " + selected.getUsername() + "?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                AppContext.authService.removeEmployee(selected.getId());
                employeeList.remove(selected);
                clearForm();
                errorLabel.setStyle("-fx-text-fill: #10B981;");
                errorLabel.setText("Empleado eliminado.");
            } catch (Exception e) {
                errorLabel.setText("Error: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleClear() {
        clearForm();
    }

    private void clearForm() {
        nameField.clear();
        usernameField.clear();
        passwordField.clear();
        passwordField.setPromptText("Contraseña");
        table.getSelectionModel().clearSelection();
        errorLabel.setText("");
    }

    @FXML
    private void goToDashboard() {
        root.getScene().setRoot(Navigation.load("/com/gympos/view/dashboard.fxml"));
    }
}