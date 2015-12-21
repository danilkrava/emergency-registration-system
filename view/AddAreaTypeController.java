package view;

import dao.AreaTypeDao;
import dao.DaoFactory;
import dao.OrganisationDao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.AreaType;
import model.Organisation;
import model.Region;
import model.TimeType;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Крава on 20.12.2015.
 */
public class AddAreaTypeController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField areaField;

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
            AreaType areaType = new AreaType(nameField.getText(), Double.parseDouble(areaField.getText()));
            AreaTypeDao dao;
            try (Connection con = DaoFactory.getConnection()) {
                dao = DaoFactory.getAreaTypeDao(con);
                dao.add(areaType);
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
            Double.parseDouble(areaField.getText());
            if (areaField.getText() == null || areaField.getText().length() == 0 || Double.parseDouble(areaField.getText()) < 0) {
                errorMessage += "Площа\n";
            }
        } catch (Exception ex) {
            errorMessage += "Площа\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Message.showErrorMessage("Виправте невірно вказані поля: \n" + errorMessage);
            return false;
        }
    }
}
