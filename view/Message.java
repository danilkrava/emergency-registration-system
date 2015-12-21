package view;

import javafx.scene.control.Alert;

/**
 * Created by Крава on 22.12.2015.
 */
public class Message {

    public static void showErrorMessage(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        showMessage(alert, title, text);
    }

    public static void showInformationMessage(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        showMessage(alert, title, text);
    }

    public static void showConfirmationnMessage(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        showMessage(alert, title, text);
    }

    private static void showMessage(Alert alert, String title, String text) {
        alert.setTitle(title);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
