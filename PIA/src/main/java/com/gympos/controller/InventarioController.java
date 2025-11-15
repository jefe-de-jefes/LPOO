package com.gympos.controller;

import java.util.Optional;

import com.gympos.app.AppContext;
import com.gympos.app.Navigation;
import com.gympos.model.InventoryItem;
import com.gympos.util.Validation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class InventarioController {
    @FXML private BorderPane root;
    @FXML private TableView<InventoryItem> table;
    @FXML private TableColumn<InventoryItem, String> colName;
    @FXML private TableColumn<InventoryItem, Integer> colQty;
    @FXML private TableColumn<InventoryItem, String> colLocation;
    
    @FXML private TextField nameField;
    @FXML private TextField qtyField;
    @FXML private TextField locationField;
    @FXML private Label errorLabel;

    private ObservableList<InventoryItem> itemList;

    @FXML
    public void initialize() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));


        itemList = FXCollections.observableArrayList(AppContext.inventoryService.getAllItems());
        table.setItems(itemList);
        
        table.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            if (n != null) {
                nameField.setText(n.getName());
                qtyField.setText(String.valueOf(n.getQuantity()));
                locationField.setText(n.getLocation());
                nameField.setDisable(true); 
            } else {
                nameField.setDisable(false);
            }
        });
    }

    private String generateIdByName(String name) {
        if (name == null) return "";
        return "INV-" + name.trim().toUpperCase().replaceAll("\\s+", "_");
    }

    @FXML
    private void handleAdd() {
        try {
            String name = nameField.getText();
            if (Validation.isNullOrEmpty(name)) throw new Exception("Nombre requerido.");
            if (Validation.isNullOrEmpty(qtyField.getText())) throw new Exception("Cantidad requerida.");

            int qty;
            try {
                qty = Integer.parseInt(qtyField.getText());
                if (qty < 0) throw new Exception("La cantidad no puede ser negativa.");
            } catch (NumberFormatException e) {
                throw new Exception("La cantidad debe ser número entero.");
            }

            String generatedId = generateIdByName(name);

            InventoryItem existingItem = AppContext.inventoryService.getItemById(generatedId);

            if (existingItem != null) {
                int newTotal = existingItem.getQuantity() + qty;
                existingItem.setQuantity(newTotal);
                if (!Validation.isNullOrEmpty(locationField.getText())) {
                }
                AppContext.inventoryService.updateItem(existingItem);
                
                errorLabel.setText("Producto existente. Cantidad actualizada a: " + newTotal);
                errorLabel.setStyle("-fx-text-fill: #F59E0B;"); 
            } else {
                InventoryItem newItem = new InventoryItem(generatedId, name, qty, locationField.getText());
                AppContext.inventoryService.addItem(newItem);
                itemList.add(newItem);
                
                errorLabel.setText("Nuevo producto registrado: " + generatedId);
                errorLabel.setStyle("-fx-text-fill: #10B981;");
            }
            
            table.refresh();
            clear();

        } catch (Exception e) {
            errorLabel.setText("Error: " + e.getMessage());
            errorLabel.setStyle("-fx-text-fill: #FCA5A5;");
        }
    }

    @FXML
    private void handleUpdate() {
        InventoryItem selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            errorLabel.setText("Seleccione un item de la tabla.");
            return;
        }

        try {
            int qty = Integer.parseInt(qtyField.getText());
            if (qty < 0) throw new Exception("Cantidad inválida.");

            selected.setQuantity(qty);
            selected.setLocation(locationField.getText());

            AppContext.inventoryService.updateItem(selected);
            table.refresh();
            
            errorLabel.setText("Item actualizado.");
            errorLabel.setStyle("-fx-text-fill: #10B981;");
            clear();

        } catch (Exception e) {
            errorLabel.setText("Error al actualizar: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        InventoryItem selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Eliminación");
        alert.setHeaderText("¿Eliminar " + selected.getName() + "?");
        alert.setContentText("Se borrará este tipo de artículo del inventario.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            AppContext.inventoryService.removeItem(selected.getId());
            itemList.remove(selected);
            clear();
            errorLabel.setText("Eliminado.");
        }
    }
    
    @FXML
    private void handleClear() { // Botón para limpiar y permitir agregar nuevo
        clear();
        errorLabel.setText("");
    }

    private void clear() {
        nameField.clear(); 
        qtyField.clear(); 
        locationField.clear();
        nameField.setDisable(false); // Habilitar nombre para nuevos registros
        table.getSelectionModel().clearSelection();
    }

    @FXML
    private void goToDashboard() {
        root.getScene().setRoot(Navigation.load("/com/gympos/view/dashboard.fxml"));
    }
}