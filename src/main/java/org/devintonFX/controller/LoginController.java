package org.devintonFX.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import org.devintonFX.services.AuthService;
import org.devintonFX.util.SceneNavigator;
import javafx.scene.control.TextField;
//import java.awt.*;

public class LoginController {
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    AuthService authService = new AuthService();
    @FXML
    public void submitLogin() {

        authService.login(email.getText(), password.getText());

        SceneNavigator.goToDashboard();
    }


    @FXML
    public void handleToRegister() {
        SceneNavigator.goToRegister();
    }
}
