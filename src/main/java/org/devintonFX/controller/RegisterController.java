package org.devintonFX.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.devintonFX.services.AuthService;
import org.devintonFX.util.SceneNavigator;

import java.awt.*;


public class RegisterController {
    @FXML
    TextField fullname;
    @FXML
    TextField email;
    @FXML
    TextField role;
    @FXML
    TextField password;

    AuthService authService = new AuthService();
    @FXML
    public void handleToRegister() {
        System.out.println(fullname.getText());
        System.out.println(email.getText());
        System.out.println(role.getText());
        System.out.println(password.getText());

        authService.register(fullname.getText(), email.getText(), password.getText());

    }

    @FXML
    public void handleToLogin() {
        SceneNavigator.goToLogin();
    }
}
