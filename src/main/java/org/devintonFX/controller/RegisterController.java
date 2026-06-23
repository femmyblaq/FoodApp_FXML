package org.devintonFX.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.devintonFX.services.AuthService;
import org.devintonFX.util.SceneNavigator;

//import java.awt.*;


public class RegisterController {
    @FXML
    TextField fullname;

    @FXML
    TextField email;

    @FXML
    TextField role;

    @FXML
    PasswordField password;

    @FXML
    private Label fullnameError;

    @FXML
    private Label emailError;

    @FXML
    private Label roleError;

    @FXML
    private Label passwordError;

    AuthService authService = new AuthService();

    private boolean validateFields() {

        boolean valid = true;

        clearErrors();

        if (fullname.getText().trim().isEmpty()) {
            showError(fullname, fullnameError,
                    "Please enter your fullname");
            valid = false;
        }

        if (email.getText().trim().isEmpty()) {
            showError(email, emailError,
                    "Please enter your email");
            valid = false;
        }

        if (role.getText().trim().isEmpty()) {
            showError(role, roleError,
                    "Please enter your role");
            valid = false;
        }

        if (password.getText().trim().isEmpty()) {
            showError(password, passwordError,
                    "Please enter your password");
            valid = false;
        }

        return valid;
    }


    private void showError(TextField field, Label label, String message) {

        field.setStyle(
                "-fx-border-color: red;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 5;"
        );

        label.setText(message);
        label.setVisible(true);
    }


    private void clearErrors() {

        fullname.setStyle("");
        email.setStyle("");
        role.setStyle("");
        password.setStyle("");

        fullnameError.setVisible(false);
        emailError.setVisible(false);
        roleError.setVisible(false);
        passwordError.setVisible(false);
    }

    @FXML
    public void handleToRegister() {

        if (!validateFields()) {
            return;
        }

        System.out.println(fullname.getText());
        System.out.println(email.getText());
        System.out.println(role.getText());
        System.out.println(password.getText());

        authService.register(fullname.getText(), email.getText(), role.getText(), password.getText());

        SceneNavigator.goToLogin();
    }

    @FXML
    public void handleToLogin() {
        SceneNavigator.goToLogin();
    }
}
