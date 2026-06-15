package org.devintonFX.controller;

import javafx.fxml.FXML;
import org.devintonFX.util.SceneNavigator;

public class HomeController {

    @FXML
    public void handleToLogin() {
        SceneNavigator.goToLogin();
    }
}
