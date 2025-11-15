package com.gympos.controller;

import com.gympos.app.AppContext;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private BorderPane root;

    @FXML
    public void onLogin(ActionEvent e){
        try {
            AppContext.activeUser = AppContext.authService.login(usernameField.getText(), passwordField.getText());
            
            ((javafx.stage.Stage) root.getScene().getWindow()).setTitle("GymPOS - Dashboard");
            root.getScene().setRoot(com.gympos.app.Navigation.load("/com/gympos/view/dashboard.fxml"));
        } catch (Exception ex){
            errorLabel.setText(ex.getMessage());
        }
    }
}
