package view;

import dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

/**
 * Created by Крава on 20.12.2015.
 */
public class AddEmergencyController {
    @FXML
    private DatePicker dateField;
    @FXML
    private ComboBox<AreaType> areaField;
    @FXML
    private ComboBox<SeverityType> severityField;
    @FXML
    private ComboBox<Organisation> organisationField;

    private ObservableList<AreaType> areaTypes = FXCollections.observableArrayList();
    private ObservableList<SeverityType> severityTypes = FXCollections.observableArrayList();
    private ObservableList<Organisation> organisations = FXCollections.observableArrayList();

    private Stage dialogStage;
    //  private Person person;
    private boolean okClicked = false;

    public void initialize() {
        try (Connection con = DaoFactory.getConnection()) {
            AreaTypeDao areaTypeDao = DaoFactory.getAreaTypeDao(con);
            SeverityTypeDao severityTypeDao = DaoFactory.getSeverityTypeDao(con);
            OrganisationDao organisationDao = DaoFactory.getOrganisationDao(con);

            areaTypes.addAll(areaTypeDao.getAll());
            severityTypes.addAll(severityTypeDao.getAll());
            organisations.addAll(organisationDao.getAll());

            areaField.setItems(areaTypes);
            severityField.setItems(severityTypes);
            organisationField.setItems(organisations);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
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
            Emergency emergency = new Emergency(new Date(dateField.getValue().toEpochDay()), areaField.getValue(), severityField.getValue(), organisationField.getValue());
            EmergencyDao dao;
            try (Connection con = DaoFactory.getConnection()) {
                dao = DaoFactory.getEmergencyDao(con);
                dao.add(emergency);
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

        if (dateField.getValue() == null) {
            errorMessage += "No valid data!\n";
        }
        if (organisationField.getValue() == null) {
            errorMessage += "No valid organisation\n";
        }
        if (areaField.getValue() == null) {
            errorMessage += "No valid area!\n";
        }
        if (severityField.getValue() == null) {
            errorMessage += "No valid severity!\n";
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