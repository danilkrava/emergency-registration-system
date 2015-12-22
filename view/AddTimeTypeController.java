package view;

import dao.DaoFactory;
import dao.SeverityTypeDao;
import dao.TimeTypeDao;
import javafx.fxml.FXML;
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
                Message.showConfirmationnMessage("Тип додано");
            } catch (SQLException e) {
                Message.showErrorMessage(e.getMessage());
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
            errorMessage += "Назва\n";
        }
        try {
            Integer.parseInt(timeField.getText());
            if (timeField.getText() == null || nameField.getText().length() == 0 || Integer.parseInt(timeField.getText()) < 0) {
                errorMessage += "Час\n";
            }
        } catch (Exception ex) {
            errorMessage += "Час\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Message.showErrorMessage("Виправте невірно вказані поля: \n" + errorMessage);
            return false;
        }
    }
}
