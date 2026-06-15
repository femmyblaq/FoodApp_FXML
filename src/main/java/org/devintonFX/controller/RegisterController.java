package org.devintonFX.controller;

import javafx.fxml.FXML;
import org.devintonFX.util.SceneNavigator;

public class RegisterController {

    @FXML
    public void handleToRegister() {
        SceneNavigator.goToRegister();
    }
}
