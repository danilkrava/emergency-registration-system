package view;

import dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Created by Крава on 20.12.2015.
 */
public class AddReportController {
    @FXML
    private DatePicker dateField;
    @FXML
    private ComboBox<Emergency> emergencyComboBox;
    @FXML
    private TextField radiationField;
    @FXML
    private TextArea infoField;

    private ObservableList<Emergency> emergencies = FXCollections.observableArrayList();

    private Stage dialogStage;
    //  private Person person;
    private boolean okClicked = false;

    public void initialize() {
        try (Connection con = DaoFactory.getConnection()) {
            EmergencyDao emergencyDao = DaoFactory.getEmergencyDao(con);
            emergencies.addAll(emergencyDao.getAll());
        } catch (SQLException e) {
            Message.showErrorMessage(e.getMessage());
            dialogStage.close();
        }
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
            Instant instant = dateField.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            Report report = new Report(emergencyComboBox.getValue(), new Date(Date.from(instant).getTime()), Double.parseDouble(radiationField.getText()), infoField.getText() );
            ReportDao dao;
            try (Connection con = DaoFactory.getConnection()) {
                dao = DaoFactory.getReportDao(con);
                dao.add(report);
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

        if (dateField.getValue() == null || LocalDate.now().toEpochDay() - dateField.getValue().toEpochDay() < 0) {
            errorMessage += "Дата\n";
        }
        if (emergencyComboBox.getValue() == null) {
            errorMessage += "НС\n";
        }
        try {
            Double.parseDouble(radiationField.getText());
            if (radiationField.getText() == null || radiationField.getText().length() == 0 || Double.parseDouble(radiationField.getText()) < 0) {
                errorMessage += "Рівень радіації\n";
            }
        } catch (Exception ex) {
            errorMessage += "Рівень радіації\n";
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
