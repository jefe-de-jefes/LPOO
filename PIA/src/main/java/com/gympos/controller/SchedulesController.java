package com.gympos.controller;

import java.time.DayOfWeek;
import java.time.LocalTime;

import com.gympos.app.AppContext;
import com.gympos.app.Navigation;
import com.gympos.model.ClassSchedule;
import com.gympos.util.Validation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class SchedulesController {
    @FXML private BorderPane root;
    @FXML private TableView<ClassSchedule> table;
    @FXML private TableColumn<ClassSchedule, String> colClass;
    @FXML private TableColumn<ClassSchedule, String> colDay;
    @FXML private TableColumn<ClassSchedule, String> colTime; // Mostrará "10:00 - 11:00"
    @FXML private TableColumn<ClassSchedule, Integer> colCapacity;
    
    @FXML private TextField classNameField;
    @FXML private ComboBox<DayOfWeek> dayBox;
    @FXML private TextField timeField;
    @FXML private TextField durationField; // NUEVO CAMPO
    @FXML private TextField capacityField;
    @FXML private Label errorLabel;

    private ObservableList<ClassSchedule> scheduleList;

    @FXML
    public void initialize() {
        colClass.setCellValueFactory(new PropertyValueFactory<>("className"));
        colDay.setCellValueFactory(new PropertyValueFactory<>("day"));
        
        // Usamos la propiedad calculada "timeRange" del modelo
        colTime.setCellValueFactory(new PropertyValueFactory<>("timeRange")); 
        
        // Si agregaste columna capacidad en FXML
        if(colCapacity != null) colCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        dayBox.setItems(FXCollections.observableArrayList(DayOfWeek.values()));
        
        scheduleList = FXCollections.observableArrayList(AppContext.scheduleService.getAllClasses());
        table.setItems(scheduleList);

        // Llenar formulario al seleccionar
        table.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            if (n != null) {
                classNameField.setText(n.getClassName());
                dayBox.setValue(n.getDay());
                timeField.setText(n.getTime().toString());
                durationField.setText(String.valueOf(n.getDuration()));
                capacityField.setText(String.valueOf(n.getCapacity()));
            }
        });
    }

    @FXML
    private void handleAdd() {
        try {
            validateInputs();
            
            LocalTime time = LocalTime.parse(timeField.getText());
            int duration = Integer.parseInt(durationField.getText());
            int cap = Integer.parseInt(capacityField.getText());
            
            String id = "SCH-" + System.currentTimeMillis();
            ClassSchedule newClass = new ClassSchedule(id, classNameField.getText(), dayBox.getValue(), time, duration, cap);
            
            AppContext.scheduleService.addClass(newClass);
            scheduleList.add(newClass);
            
            clearForm();
            errorLabel.setText("Clase agendada: " + newClass.getTimeRange());
            errorLabel.setStyle("-fx-text-fill: #10B981;");

        } catch (Exception e) {
            errorLabel.setText("Error: " + e.getMessage());
            errorLabel.setStyle("-fx-text-fill: #FCA5A5;");
        }
    }

    @FXML
    private void handleUpdate() {
        ClassSchedule selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            errorLabel.setText("Seleccione una clase.");
            return;
        }

        try {
            validateInputs();
            
            LocalTime time = LocalTime.parse(timeField.getText());
            int duration = Integer.parseInt(durationField.getText());
            int cap = Integer.parseInt(capacityField.getText());

            // Actualizar objeto
            selected.setClassName(classNameField.getText());
            selected.setDay(dayBox.getValue());
            selected.setTime(time);
            selected.setDuration(duration);
            selected.setCapacity(cap);

            // Guardar (El servicio validará si el nuevo horario choca)
            AppContext.scheduleService.updateClass(selected);
            table.refresh();
            
            errorLabel.setText("Clase actualizada.");
            errorLabel.setStyle("-fx-text-fill: #10B981;");
            clearForm();

        } catch (Exception e) {
            errorLabel.setText("Error al actualizar: " + e.getMessage());
            errorLabel.setStyle("-fx-text-fill: #FCA5A5;");
        }
    }

    @FXML
    private void handleDelete() {
        ClassSchedule selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            AppContext.scheduleService.removeClass(selected.getId());
            scheduleList.remove(selected);
            clearForm();
            errorLabel.setText("Clase eliminada.");
        }
    }
    
    @FXML
    private void handleClear() {
        clearForm();
    }

    private void validateInputs() throws Exception {
        if (Validation.isNullOrEmpty(classNameField.getText()) || dayBox.getValue() == null) 
            throw new Exception("Nombre y Día son obligatorios");
        if (Validation.isNullOrEmpty(durationField.getText())) throw new Exception("Duración requerida");
    }

    private void clearForm() {
        classNameField.clear(); timeField.clear(); durationField.clear(); capacityField.clear();
        dayBox.getSelectionModel().clearSelection();
        table.getSelectionModel().clearSelection();
        errorLabel.setText("");
    }

    @FXML
    private void goToDashboard() {
        root.getScene().setRoot(Navigation.load("/com/gympos/view/dashboard.fxml"));
    }
}