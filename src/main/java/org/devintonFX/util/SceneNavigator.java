package org.devintonFX.util;

import org.devintonFX.Main;

public class SceneNavigator {
    public static void goToLogin() {
        try {
            Main.loadScene("/view/login.fxml", "Login");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void goToRegister() {
        try {
            Main.loadScene("/view/register.fxml", "Register");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void goToDashboard() {
        try {
            Main.loadScene("/view/CustomerDashboard.fxml", "Dashboard");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
