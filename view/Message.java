package view;

import javafx.scene.control.Alert;

/**
 * Created by Крава on 22.12.2015.
 */
public class Message {

    public static void showErrorMessage(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        showMessage(alert, "Помилка", text);
    }

    public static void showInformationMessage(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        showMessage(alert, "Увага", text);
    }

    public static void showConfirmationnMessage(String text) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        showMessage(alert, "Успіх", text);
    }

    private static void showMessage(Alert alert, String title, String text) {
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
