package view;

import dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.awt.geom.Area;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Created by Крава on 22.12.2015.
 */
public class AddMeasureController {
    @FXML
    private TextField infoField;
    @FXML
    private ComboBox<TimeType> timeTypeComboBox;
    @FXML
    private ComboBox<SeverityType> severityTypeComboBox;
    @FXML
    private ComboBox<AreaType> areaTypeComboBox;

    private ObservableList<AreaType> areaTypes = FXCollections.observableArrayList();
    private ObservableList<SeverityType> severityTypes = FXCollections.observableArrayList();
    private ObservableList<TimeType> timeTypes = FXCollections.observableArrayList();

    private Stage dialogStage;
    //  private Person person;
    private boolean okClicked = false;

    public void initialize() {
        try (Connection con = DaoFactory.getConnection()) {
            AreaTypeDao areaTypeDao = DaoFactory.getAreaTypeDao(con);
            SeverityTypeDao severityTypeDao = DaoFactory.getSeverityTypeDao(con);
            TimeTypeDao timeTypeDao = DaoFactory.getTimeTypeDao(con);

            areaTypes.addAll(areaTypeDao.getAll());
            severityTypes.addAll(severityTypeDao.getAll());
            timeTypes.addAll(timeTypeDao.getAll());

            areaTypeComboBox.setItems(areaTypes);
            severityTypeComboBox.setItems(severityTypes);
            timeTypeComboBox.setItems(timeTypes);
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

            Measure measure = new Measure(timeTypeComboBox.getValue(), severityTypeComboBox.getValue(), areaTypeComboBox.getValue(), infoField.getText());
            MeasureDao dao;
            try (Connection con = DaoFactory.getConnection()) {
                dao = DaoFactory.getMeasureDao(con);
                dao.add(measure);
                Message.showConfirmationnMessage("Рекомендацію додано");
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

        if (infoField.getText() == null || infoField.getText().length() == 0) {
            errorMessage += "Інфо\n";
        }
        if (timeTypeComboBox.getValue() == null) {
            errorMessage += "Тип за часом\n";
        }
        if (areaTypeComboBox.getValue() == null) {
            errorMessage += "Тип за площею\n";
        }
        if (severityTypeComboBox.getValue() == null) {
            errorMessage += "Тип за важкістю\n";
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

