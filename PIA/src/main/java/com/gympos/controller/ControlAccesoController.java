package com.gympos.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.gympos.app.AppContext;
import com.gympos.app.Navigation;
import com.gympos.model.ClassSchedule;
import com.gympos.model.Client;
import com.gympos.util.Exceptions.AccessDeniedException;
import com.gympos.util.Validation;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

public class ControlAccesoController {

    @FXML private BorderPane root;
    @FXML private TextField clientIdField;
    @FXML private Label statusLabel;
    @FXML private ComboBox<ClassSchedule> classSelector;

    @FXML
    public void initialize() {
        loadTodaysClasses();
    }

    private void loadTodaysClasses() {
        LocalDate today = LocalDate.now();
        List<ClassSchedule> todaysClasses = AppContext.scheduleService.getAllClasses().stream()
            .filter(c -> c.getDay() == today.getDayOfWeek())
            .collect(Collectors.toList());

        classSelector.setItems(FXCollections.observableArrayList(todaysClasses));
        
        classSelector.setConverter(new StringConverter<ClassSchedule>() {
            @Override
            public String toString(ClassSchedule object) {
                if (object == null) return "Entrada General (Gimnasio)";
                return object.getClassName() + " - " + object.getTime() + " (" + object.getDuration() + " min)";
            }
            @Override
            public ClassSchedule fromString(String string) { return null; }
        });
    }

    @FXML
    private void handleRegisterEntry() {
        try {
            String clientId = clientIdField.getText();
            if (Validation.isNullOrEmpty(clientId)) throw new Exception("Se requiere ID de cliente.");

            Client client = AppContext.clientService.getClientById(clientId);
            if (client == null) throw new Exception("Cliente no encontrado.");

            ClassSchedule selectedClass = classSelector.getValue();

            if (selectedClass == null) {
                AppContext.accessService.registerEntry(client);
                statusLabel.setTextFill(Color.GREENYELLOW);
                statusLabel.setText("BIENVENIDO AL GYM: " + client.getFullName());
            } else {
                AppContext.accessService.registerClassEntry(client, selectedClass);
                statusLabel.setTextFill(Color.GREENYELLOW);
                statusLabel.setText("ACCESO A CLASE: " + selectedClass.getClassName());
            }

        } catch (AccessDeniedException e) {
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText(e.getMessage());
        } catch (Exception e) {
            statusLabel.setTextFill(Color.ORANGERED);
            statusLabel.setText("Error: " + e.getMessage());
        }
        
        clientIdField.clear();
        classSelector.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleRegisterExit() {
        try {
            String clientId = clientIdField.getText();
            if (Validation.isNullOrEmpty(clientId)) throw new Exception("Se requiere ID.");

            Client client = AppContext.clientService.getClientById(clientId);
            if (client == null) throw new Exception("Cliente no encontrado.");

            AppContext.accessService.registerExit(client);
            
            statusLabel.setTextFill(Color.LIGHTGRAY);
            statusLabel.setText("HASTA LUEGO: " + client.getFullName());

        } catch (Exception e) {
            statusLabel.setTextFill(Color.ORANGERED);
            statusLabel.setText("Error: " + e.getMessage());
        }
        clientIdField.clear();
    }

    @FXML
    private void goToDashboard() {
        root.getScene().setRoot(Navigation.load("/com/gympos/view/dashboard.fxml"));
    }
}