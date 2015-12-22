package view;

import dao.AreaTypeDao;
import dao.DaoFactory;
import dao.RegionDao;
import dao.SeverityTypeDao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.AreaType;
import model.Region;
import model.SeverityType;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Крава on 20.12.2015.
 */
public class AddSeverityTypeController {
    @FXML
    private TextField nameField;

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
            SeverityType severityType = new SeverityType(nameField.getText());
            SeverityTypeDao dao;
            try (Connection con = DaoFactory.getConnection()) {
                dao = DaoFactory.getSeverityTypeDao(con);
                dao.add(severityType);
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
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Message.showErrorMessage("Виправте невірно вказані поля: \n" + errorMessage);
            return false;
        }
    }
}
