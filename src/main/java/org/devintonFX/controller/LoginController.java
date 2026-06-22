package org.devintonFX.controller;

import javafx.fxml.FXML;
import org.devintonFX.util.SceneNavigator;

public class LoginController {

    @FXML
    public void submitLogin() {
        SceneNavigator.goToDashboard();
    }


    @FXML
    public void handleToRegister() {
        SceneNavigator.goToRegister();
    }
}
