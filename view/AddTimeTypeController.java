package view;

import dao.DaoFactory;
import dao.SeverityTypeDao;
import dao.TimeTypeDao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.SeverityType;
import model.TimeType;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Крава on 21.12.2015.
 */
public class AddTimeTypeController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField timeField;

    private Stage dialogStage;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }


    @FXML
    private void handleOk() {
        if (isInputValid()) {
            TimeType timeType = new TimeType(nameField.getText(), Integer.parseInt(timeField.getText()));
            TimeTypeDao dao;
            try (Connection con = DaoFactory.getConnection()) {
                dao = DaoFactory.getTimeTypeDao(con);
                dao.add(timeType);
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
            okClicked = true;
            dialogStage.close();
        }
    }


    @FXML
    private void handleCancel() {
        dialogStage.close();
    }


    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "No valid name!\n";
        }
        try {
            Integer.parseInt(timeField.getText());
        } catch (Exception ex) {
            errorMessage += "No valid time!\n";
        }
        if (timeField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "No valid time!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            return false;
        }
    }
}
