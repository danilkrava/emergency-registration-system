package view;

import javafx.scene.control.Alert;

/**
 * Created by Крава on 22.12.2015.
 */
public class Message {

    public static void showErrorMessage(Alert.AlertType type, String title, String text) {
        Alert alert = new Alert(type);
        // alert.initOwner(dialogStage);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public static void showMessage(Alert.AlertType type, String title, String text) {
        Alert alert = new Alert(type);
        //alert.initOwner(dialogStage);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.showAndWait();
    }

}
